package com.funkyjester.demo.integration.config;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.salesforce.SalesforceEndpointConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Author:  gabe
 * Date:    2020-05-05
 * Project: salesforce-prototype
 */
@Configuration
@Getter
@ToString
@Slf4j
public class SalesforceContext {

    // SYSTEM properties
    //@Value("${salesforce.system.api.version}")
    String apiVersion = SalesforceEndpointConfig.DEFAULT_VERSION;

    // AUTH properties
    @Value("${salesforce.clientId}")
    public String clientId;
    @Value("${salesforce.clientSecret}")
    public String clientSecret;
    @Value("${salesforce.userName}")
    public String userName;
    @Value("${salesforce.password}")
    public String password;
    @Value("${salesforce.token}")
    public String token;
    @Value("${salesforce.loginUrl:https://login.salesforce.com}")
    public String loginUrl;

    @Value("${salesforce.app.notify.label}")
    public String notifyLabel;

    @Value("${salesforce.system.response.format}")
    public String salesforceResponseFormat;

    //@PostConstruct
    public void dumpState() {
        log.info("----\n{}\n----", this.toString());
    }
}
