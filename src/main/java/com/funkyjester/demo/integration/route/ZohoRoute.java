package com.funkyjester.demo.integration.route;

import com.funkyjester.demo.integration.config.Constants;
import com.funkyjester.demo.integration.config.ZohoContext;
import com.funkyjester.demo.integration.model.Action;
import com.funkyjester.demo.integration.model.SOR;
import com.funkyjester.demo.integration.model.common.Account;
import com.funkyjester.demo.integration.model.common.Contact;
import com.funkyjester.demo.integration.model.common.User;
import com.funkyjester.demo.integration.service.ZohoAPIClient;
import com.google.common.collect.Lists;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * camel routes touching ZohoCRM
 * Also, scheduler entry to trigger period polling of Zoho APIs
 *
 */
@Configuration
public class ZohoRoute extends RouteBuilder {
    public static final String ZOHO_RECORD_TYPE_HEADER = "zohoRecordType";
    @Autowired
    ZohoContext config;

    @Override
    public void configure() throws Exception {
        onException()
            .to("bean:exceptionNotifier?method=rollBackWatermark")
            .to("bean:exceptionNotifier?method=sendNotification")
            .maximumRedeliveries(1);

        String recipientList = "";
        for (String i : Lists.newArrayList("user", "account", "contact")) {
            if (Strings.isNotEmpty(recipientList)) {
                recipientList += ",";
            }
            recipientList += "jms:topic:crm.event." + i + ".delta";
        }
        from("quartz://zoho/deltaTimer?autoStartScheduler=true&cron=0+*+*+*+*+?")
                .autoStartup(true)
                .routeId("DeltaSchedulerRoute")
                .log(LoggingLevel.INFO, "Delta Event Poll Triggered")
                .setExchangePattern(ExchangePattern.InOnly)
                .setHeader(Constants.HEADER_ACTION, simple(Action.UPDATE.toString()))
                .setHeader(Constants.HEADER_SOURCE, simple(SOR.ZOHO.toString()))
                .recipientList(constant(recipientList));


        from("jms:topic:crm.event.user.delta?subscriptionDurable=false")
                .autoStartup(true)
                .routeId("ZohoUserDelta")
                .setHeader(Constants.HEADER_RECORDTYPE, simple(ZohoAPIClient.RES_USERS))
                .to("bean:zohoAPIClient?method=getUpdatedUsers")
                .split(body())
                .convertBodyTo(User.class)
                .marshal().json(JsonLibrary.Jackson)
                .to("jms:topic:crm.zoho.user?deliveryPersistent=true");
        from("jms:topic:crm.event.contact.delta?subscriptionDurable=false")
                .autoStartup(true)
                .routeId("ZohoContactDelta")
                .setHeader(Constants.HEADER_RECORDTYPE, simple(ZohoAPIClient.RES_CONTACTS))
                .to("bean:zohoAPIClient?method=getUpdatedContacts")
                .split(body())
                .convertBodyTo(Contact.class)
                .marshal().json(JsonLibrary.Jackson)
                .to("jms:topic:crm.zoho.contact?deliveryPersistent=true");
        from("jms:topic:crm.event.account.delta?subscriptionDurable=false")
                .autoStartup(true)
                .routeId("ZohoAccountDelta")
                .setHeader(Constants.HEADER_RECORDTYPE, simple(ZohoAPIClient.RES_ACCOUNTS))
                .to("bean:zohoAPIClient?method=getUpdatedAccounts")
                .split(body())
                .convertBodyTo(Account.class)
                .marshal().json(JsonLibrary.Jackson)
                .to("jms:topic:crm.zoho.account?deliveryPersistent=true");

        from("jms:topic:crm.event.user.all?subscriptionDurable=false")
                .autoStartup(true)
                .routeId("ZohoUserAll")
                .setHeader(Constants.HEADER_RECORDTYPE, simple(ZohoAPIClient.RES_USERS))
                .to("bean:zohoAPIClient?method=getUsers")
                .split(body())
                .convertBodyTo(User.class)
                .marshal().json(JsonLibrary.Jackson)
                .to("jms:topic:crm.zoho.user?deliveryPersistent=true");
        from("jms:topic:crm.event.contact.all?subscriptionDurable=false")
                .autoStartup(true)
                .routeId("ZohoContactAll")
                .setHeader(Constants.HEADER_RECORDTYPE, simple(ZohoAPIClient.RES_CONTACTS))
                .to("bean:zohoAPIClient?method=getContacts")
                .split(body())
                .convertBodyTo(Contact.class)
                .marshal().json(JsonLibrary.Jackson)
                .to("jms:topic:crm.zoho.contact?deliveryPersistent=true");
        from("jms:topic:crm.event.account.all?subscriptionDurable=false")
                .autoStartup(true)
                .routeId("ZohoAccountAll")
                .setHeader(Constants.HEADER_RECORDTYPE, simple(ZohoAPIClient.RES_ACCOUNTS))
                .to("bean:zohoAPIClient?method=getAccounts")
                .split(body())
                .convertBodyTo(Account.class)
                .marshal().json(JsonLibrary.Jackson)
                .to("jms:topic:crm.zoho.account?deliveryPersistent=true");
    }
}
