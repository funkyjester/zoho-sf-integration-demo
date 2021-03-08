package com.funkyjester.demo.integration.route;

import com.funkyjester.demo.integration.config.ZohoPropertyConfig;
import com.funkyjester.demo.integration.component.ZohoAPIAuthAgent;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfOperationException;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.camel.model.DataFormatDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZohoRoute extends RouteBuilder {
    public static final String ZOHO_RECORD_TYPE_HEADER = "zohoRecordType";
    @Autowired
    ZohoPropertyConfig config;

    @Autowired
    ZohoAPIAuthAgent authAgent;

    @Override
    public void configure() throws Exception {
        onException(CxfOperationException.class)
                .handled(true)
                .bean(authAgent, "doRefreshAuth")
                .maximumRedeliveries(1)
                .useOriginalMessage()
                //.to("seda:request")
                .end();

        from("direct:foo")
                .autoStartup(true)
                .to("bean:zohoAPIClient?method=getAccounts");
                //.to("jms:topic:accounts?deliveryPersistent=true");

        //from("jms:topic:accounts?durableSubscriptionName=sf")
        //        .to("log:sfSub?level=INFO&showAll=true");

        from("seda:request")
                .log("${headers}, ${body}")
                .toD("cxfrs://" + config.getUrl() + config.getRootpath() + "${header." + ZOHO_RECORD_TYPE_HEADER + "}");

    }
}
