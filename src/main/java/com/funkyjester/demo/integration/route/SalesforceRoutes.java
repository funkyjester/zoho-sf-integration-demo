package com.funkyjester.demo.integration.route;

import com.funkyjester.demo.integration.config.SalesforceContext;
import com.funkyjester.demo.integration.model.common.Account;
import com.funkyjester.demo.integration.model.common.Contact;
import com.funkyjester.demo.integration.model.common.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static org.apache.camel.component.salesforce.SalesforceEndpointConfig.*;

/**
 * these are routes to salesforce
 * Entry point is subscription boundary on pub/sub
 *
 */
@Configuration
public class SalesforceRoutes extends RouteBuilder {
    @Autowired
    SalesforceContext salesforceContext;
    @Override
    public void configure() throws Exception {
        onException()
            .to("bean:exceptionNotifier?method=sendNotification")
            .maximumRedeliveries(1);

        from("jms:topic:crm.zoho.account?durableSubscriptionName=sf&clientId=sc_account")
                .unmarshal(new JacksonDataFormat(Account.class))
                .convertBodyTo(org.apache.camel.salesforce.dto.Account.class)
                .to("bean:salesforceClient?method=upsertRecord")
                .to("log:upsertRecordResponse?showAll=true");

        from("jms:topic:crm.zoho.contact?durableSubscriptionName=sf&clientId=sf_contact")
                .unmarshal(new JacksonDataFormat(Contact.class))
                .convertBodyTo(org.apache.camel.salesforce.dto.Contact.class)
                .to("bean:salesforceClient?method=upsertRecord")
                .to("log:upsertRecordResponse?showAll=true");

        from("jms:topic:crm.zoho.user?durableSubscriptionName=sf&clientId=sf_user")
                .unmarshal(new JacksonDataFormat(User.class))
                .convertBodyTo(org.apache.camel.salesforce.dto.User.class)
                .to("bean:salesforceClient?method=upsertRecord")
                .to("log:upsertRecordResponse?showAll=true");
    }
}
