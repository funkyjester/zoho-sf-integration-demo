package com.funkyjester.demo.integration.service;

import com.funkyjester.demo.integration.component.ExceptionNotifier;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@SpringBootTest(properties = { "slack.webhook=seda:testendpoint" })
public class AlertTest {
    @Autowired
    ExceptionNotifier exceptionNotifier;
    @Autowired
    CamelContext camelContext;

    @Test
    public void testSlackOut() throws Exception {
        ConsumerTemplate ct = camelContext.createConsumerTemplate();
        String word = "hello world!";
        exceptionNotifier.sendNotification(word, null);
        ct.cleanUp();
        Exchange receive = ct.receive("seda:testendpoint", 5000l);
        Assert.assertNotNull(receive);
        Assert.assertTrue(receive.getIn().getBody(String.class).contains(word));
    }

    @Test
    public void testSlackOutOnCamelRoute() throws Exception {
        ConsumerTemplate ct = camelContext.createConsumerTemplate();
        String word = "hello world!";
        ProducerTemplate pt = camelContext.createProducerTemplate();
        pt.asyncSendBody("jms:topic:crm.zoho.user", word);
        ct.cleanUp();
        Exchange receive = ct.receive("seda:testendpoint", 5000l);
        Assert.assertNotNull(receive);
        Assert.assertTrue(receive.getIn().getBody(String.class).contains("Unrecognized token")); // this should be error message from JsonParseException
    }
}
