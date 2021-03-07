package com.funkyjester.demo.integration.service;

import com.funkyjester.demo.integration.config.ZohoConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@Slf4j
public class ZohoAPIAuthAgent {
    @Autowired
    ZohoConfig config;

    @Autowired
    CamelContext camelContext;

    private String template = null;

    private String constructRefreshUrl() {
        if (template == null) {
            if (config != null && config.getAuth() != null) {
                template = config.getAuth().get("refresh.template");
                for (Map.Entry<String, String> e : config.getAuth().entrySet()) {
                    String key = "__" + e.getKey() + "__";
                    template = template.replaceAll(key, e.getValue());
                }
            }
        }
        return template;
    }

    public void doRefreshAuth() throws Exception {
        final FluentProducerTemplate pt = camelContext.createFluentProducerTemplate();
        String url = constructRefreshUrl();
        final String response = pt.to(url).request(String.class);
        log.info("response: {}", response);
    }

    @PostConstruct
    public void doMe() {
        log.info("zoho config: {}", config.toString());
        try {
            doRefreshAuth();
        } catch (Exception e) {
            log.error("Failed on refresh auth", e);
        }
    }
}
