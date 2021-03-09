package com.funkyjester.demo.integration.route;

import com.funkyjester.demo.integration.config.Constants;
import com.funkyjester.demo.integration.config.ZohoContext;
import com.funkyjester.demo.integration.model.Action;
import com.funkyjester.demo.integration.model.SOR;
import com.funkyjester.demo.integration.model.common.Account;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZohoRoute extends RouteBuilder {
    public static final String ZOHO_RECORD_TYPE_HEADER = "zohoRecordType";
    @Autowired
    ZohoContext config;

    @Override
    public void configure() throws Exception {
        //onException(CxfOperationException.class)
        //        .handled(true)
        //        .maximumRedeliveries(1)
        //        .useOriginalMessage()
        //        .to("seda:request")
        //        .end();

        from("jms:topic:allAccounts")
                .autoStartup(true)
                .to("bean:zohoAPIClient?method=getAccounts")
                .setHeader(Constants.HEADER_RECORDTYPE, simple("Account"))
                .setHeader(Constants.HEADER_ACTION, simple(Action.UPDATE.toString()))
                .setHeader(Constants.HEADER_SOURCE, simple(SOR.ZOHO.toString()))
                .split(body())

                .convertBodyTo(Account.class)
                .marshal().json(JsonLibrary.Jackson)
                //.to("log:foo?level=INFO&showAll=false&showBody=true")
                //.to("file:/Users/gabe/work/20210310/in/");
                .to("jms:topic:accounts?deliveryPersistent=true");


        from("seda:request")
                .log("${headers}, ${body}")
                .toD("cxfrs://" + config.getUrl() + config.getRootpath() + "${header." + ZOHO_RECORD_TYPE_HEADER + "}");

    }
}
