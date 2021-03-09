package com.funkyjester.demo.integration.config.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Getter @Setter
@Profile("!local")
@Slf4j
public class AppNonLocalConfig {
}
