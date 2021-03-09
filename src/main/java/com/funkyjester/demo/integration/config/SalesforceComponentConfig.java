package com.funkyjester.demo.integration.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.salesforce.SalesforceComponent;
import org.apache.camel.component.salesforce.SalesforceEndpointConfig;
import org.apache.camel.component.salesforce.SalesforceLoginConfig;
import org.apache.camel.salesforce.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@Slf4j
public class SalesforceComponentConfig {
    @Autowired
    SalesforceContext salesforceContext;

    @Bean
    SalesforceLoginConfig salesforceLoginConfig() {
        SalesforceLoginConfig loginConfig = new SalesforceLoginConfig();
        loginConfig.setLoginUrl(salesforceContext.getLoginUrl());
        loginConfig.setClientId(salesforceContext.getClientId());
        loginConfig.setClientSecret(salesforceContext.getClientSecret());
        loginConfig.setUserName(salesforceContext.getUserName());
        //loginConfig.setPassword(salesforceContext.getPassword());
        loginConfig.setPassword(String.format("%s%s", salesforceContext.getPassword(), salesforceContext.getToken()));
        return loginConfig;
    }

    @Bean("salesforce")
    SalesforceComponent salesforceComponent(SalesforceLoginConfig loginConfig) throws Exception {
        SalesforceComponent component = new SalesforceComponent();
        final SalesforceEndpointConfig config = new SalesforceEndpointConfig();
        config.setApiVersion(salesforceContext.getApiVersion());
        component.setConfig(config);
        component.setLoginConfig(loginConfig);

        HashMap<String, Object> clientProperties = new HashMap<>();
        clientProperties.put("timeout", "60000");
        clientProperties.put("maxRetries", "3");
        clientProperties.put("maxContentLength", String.valueOf(4 * 1024 * 1024));
        component.setHttpClientProperties(clientProperties);

        component.setPackages(new String[] {Account.class.getPackage().getName()});
        return component;
    }
}
