package com.funkyjester.demo.integration.config;

import com.funkyjester.demo.integration.component.ZohoAPIAuthAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Configuration
@Slf4j
public class BeanConfig {
    @Autowired
    ZohoPropertyConfig zohoConfig;

    @Autowired
    ZohoAPIAuthAgent authAgent;

    @Bean
    public Client RSWebClient() {
        final ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        final javax.ws.rs.client.Client client = clientBuilder.build();
        client.property("http.autoredirect", Boolean.TRUE);
        client.register(authAgent);
        return client;
    }

    @Bean
    public WebTarget zohoClient(Client client) {
        return client.target(zohoConfig.getUrl() + zohoConfig.getRootpath());
    }


}
