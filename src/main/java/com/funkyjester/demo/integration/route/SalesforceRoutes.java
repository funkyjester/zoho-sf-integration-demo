package com.funkyjester.demo.integration.route;

import com.funkyjester.demo.integration.config.SalesforceContext;
import com.funkyjester.demo.integration.model.common.Account;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static org.apache.camel.component.salesforce.SalesforceEndpointConfig.*;

@Configuration
public class SalesforceRoutes extends RouteBuilder {
    @Autowired
    SalesforceContext salesforceContext;
    @Override
    public void configure() throws Exception {
        //onException(Exception.class)
        //        .to("bean:exceptionNotifier?method=sendNotification")
        //        .end();
        //        .handled(true)
        //        .maximumRedeliveries(1)
        //        .useOriginalMessage()
        //        .to("seda:request")
        //        .end();

        from("jms:topic:accounts?durableSubscriptionName=sf&clientId=aaa")
                .unmarshal(new JacksonDataFormat(Account.class))
                .convertBodyTo(org.apache.camel.salesforce.dto.Account.class)
                .to("bean:salesforceClient?method=upsertRecord")
                .to("log:upsertRecordResponse?showAll=true");

            //.to("file:/Users/gabe/work/20210310/out/");
            //.setHeader(FORMAT, constant(salesforceContext.getSalesforceResponseFormat()))
            //.setHeader(SOBJECT_NAME, constant(Account.class.getSimpleName()))
            //.setHeader(SOBJECT_EXT_ID_NAME, constant("zohoId__c"))
            //.to("salesforce:upsertSObject");

        from("direct:querysf")
                .setHeader(FORMAT, constant(salesforceContext.getSalesforceResponseFormat()))
                .to("salesforce:query");
    }
}
