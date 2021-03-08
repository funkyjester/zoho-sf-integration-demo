package com.funkyjester.demo.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.FluentProducerTemplate;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import org.apache.cxf.jaxrs.client.Client;

@Slf4j
@SpringBootTest
public class ServiceTest {
    //@Autowired
    //JAXRSClientFactoryBean zohoClientFactory;

    @Autowired
    ZohoAPIClient zohoClient;

    @Autowired
    CamelContext camelContext;

    @Test
    public void testServiceCallAccount() throws Exception {

        Assert.assertNotNull(zohoClient);
        //final Client client = zohoClientFactory.create();
        log.info("Response: {}", zohoClient.getAccounts());
    }

    @Test
    public void testCamelServiceCall() throws Exception {
        FluentProducerTemplate pt = camelContext.createFluentProducerTemplate();
        String output = pt.to("cxfrs:bean:zohoClient:getAccounts").request(String.class);
    }

}
