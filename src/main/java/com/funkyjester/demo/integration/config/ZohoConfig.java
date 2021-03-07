package com.funkyjester.demo.integration.config;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "zoho.api")
@Getter
@ToString
@Slf4j
public class ZohoConfig {
    Map<String, String> auth = Maps.newHashMap();

    //@PostConstruct
    public void doPostConstruct() {
        log.info("this: {}", this.toString());
    }
}

