package com.funkyjester.demo.integration.component;

import com.funkyjester.demo.integration.config.ZohoPropertyConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.component.http.HttpMethods;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.camel.builder.Builder.constant;

@Component
@Slf4j
public class ZohoAPIAuthAgent implements PhaseInterceptor, ClientRequestFilter {
    @Autowired
    ZohoPropertyConfig config;

    @Autowired
    CamelContext camelContext;

    @Getter @Setter
    Optional<String> access_token = Optional.empty();
    @Getter @Setter
    Optional<String> refresh_token = Optional.empty();
    @Getter @Setter
    String api_domain;
    @Getter @Setter
    String token_type;
    @Getter @Setter
    int token_expiry;
    @Getter @Setter
    Optional<String> generated_token = Optional.empty();

    AtomicBoolean initialized;
    AtomicBoolean authValid;

    private Optional<String> tokenRefreshUrl = Optional.empty();

    private void initialize() {
        initialized = new AtomicBoolean(false);
        authValid = new AtomicBoolean(false);

        if (config != null) {
            if (config.getAuth().containsKey("generated.token")) {
                initialized.set(false);
            }
            generated_token = Optional.ofNullable(config.getAuth().get("generated.token"));
            if (!generated_token.isPresent()) {
                access_token = Optional.ofNullable(config.getAuth().get("access.token"));
                refresh_token = Optional.ofNullable(config.getAuth().get("refresh.token"));
            }
        }
    }
    private String constructRefreshUrl() {
        if (!tokenRefreshUrl.isPresent()) {
            String template = config.getAuth().getOrDefault("refresh.template", "");
            // replace session vars first
            if (refresh_token.isPresent()) {
                template = template.replace("__refresh.token__", refresh_token.get());
            }
            if (config != null && config.getAuth() != null) {
                for (Map.Entry<String, String> e : config.getAuth().entrySet()) {
                    String key = "__" + e.getKey() + "__";
                template = template.replaceAll(key, e.getValue());
                }
            }
            tokenRefreshUrl = Optional.of(template);
        }
        return tokenRefreshUrl.get();
    }

    public boolean doRefreshAuth() {
        final FluentProducerTemplate pt = camelContext.createFluentProducerTemplate();
        String url = constructRefreshUrl();
        final String response = pt.to(url).request(String.class);
        log.info("response: {}", response);
        // assume success, expire refresh url
        authValid.set(true);
        tokenRefreshUrl = Optional.empty();
        return true;
    }

    public void doInitialAuth() throws Exception {
        final FluentProducerTemplate pt = camelContext.createFluentProducerTemplate();
        String boundary = "XX-SF-CLIENT-BOUNDARY";
        String body = generateInitialAuthBody(boundary);
        //log.info("body: {}", body);
        String response = pt.withHeader(Exchange.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA + "; boundary=" + boundary)
                .withHeader(Exchange.CONTENT_LENGTH, body.length())
                .withHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
                .withHeader(Exchange.HTTP_URI, config.getAuth().get("url") + config.getAuth().get("path"))
                .withBody(body)
                //.to("log:com.funkyjester.demo.integration.component.ZohoAPIAuthAgent?level=INFO&showAll=true")
                .to("http")
                .request(String.class);
        log.info("Initial auth response: {}", response);
    }

    @PostConstruct
    public void doInit() {
        initialize();
    }

    private String generateInitialAuthBody(String boundary) {
        String separator = "--"+boundary+System.lineSeparator();
        String disposition_template = "Content-Disposition: form-data; name=\"%s\""+System.lineSeparator()+"%s"+System.lineSeparator();
        StringBuilder sb = new StringBuilder()
                .append(separator)
                .append(String.format(disposition_template, "grant_type", "authorization_code"))
                .append(separator)
                .append(String.format(disposition_template, "client_id", config.getAuth().get("client.id")))
                .append(separator)
                .append(String.format(disposition_template, "client_secret", config.getAuth().get("client.secret")))
                .append(separator)
                .append(String.format(disposition_template, "redirect_uri", "http://127.0.0.1"))
                .append(separator)
                .append(String.format(disposition_template, "code", generated_token.get()))
                .append("--").append(boundary).append("--");
        return sb.toString();
    }

    public String getCurrentAuthHeader() {
        //if (!config.getAuth().keySet().contains("access.token")) {
        //    throw new Exception("Failed to find valid access token");
        //}
        return String.format("%s %s", config.getAuthprefix(), config.getAuth().get("access.token"));
    }

    final Set<String> empty = Sets.newHashSet();

    @Override
    public Set<String> getAfter() {
        return empty;
    }

    @Override
    public Set<String> getBefore() {
        return empty;
    }

    @Override
    public String getId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getPhase() {
        return Phase.INVOKE;
    }

    @Override
    public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
        return null;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        log.info("Intercepted message at ZohoAPIAuthAgent.handleMessage");
        Map<String, List<String>> headers = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
        if (headers == null) {
            headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
            message.put(Message.PROTOCOL_HEADERS, headers);
        }
        if (!headers.keySet().contains("Authorization")) {
            List<String> values = Lists.newArrayList(getCurrentAuthHeader());
            headers.put("Authorization", values);
        }
    }

    @Override
    public void handleFault(Message message) {

    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        log.info("Intercepted message at ZohoAPIAuthAgent.filter");
        final MultivaluedMap<String, Object> headers = clientRequestContext.getHeaders();
        if (headers != null) {
            if (!headers.keySet().contains("Authorization")) {
                headers.put("Authorization", Collections.singletonList(getCurrentAuthHeader()));
            }
        }
    }
}
