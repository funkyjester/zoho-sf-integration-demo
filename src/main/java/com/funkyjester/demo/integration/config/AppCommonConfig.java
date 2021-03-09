package com.funkyjester.demo.integration.config;

import com.funkyjester.demo.integration.convert.SalesforceConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.spi.TypeConverterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

/**
 * this is loaded for all profiles
 */
@Configuration
@Getter @Setter
@Slf4j
public class AppCommonConfig {
    @Value("${slack.webhook}")
    String slackWebhook;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CamelContext camelContext;

    /**
     * sales force converter is, for whatever reason, not being automatically register in type
     * converter registry.
     * Manually register once.
     */
    @Autowired
    SalesforceConverter salesforceConverter;

    static boolean alreadyAdded = false;
    @PostConstruct
    public void explicitTypeConverterRegistration() {
        if (!alreadyAdded) {
            TypeConverterRegistry typeConverterRegistry = camelContext.getTypeConverterRegistry();
            typeConverterRegistry.addTypeConverters(salesforceConverter);
            alreadyAdded = true;
        }
    }

}
