package com.funkyjester.demo.integration.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZohoRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:foo")
                .autoStartup(true)
                .to("direct:bar");
    }
}
