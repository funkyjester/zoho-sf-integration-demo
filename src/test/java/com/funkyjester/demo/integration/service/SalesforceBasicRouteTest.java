package com.funkyjester.demo.integration.service;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.salesforce.api.dto.SObjectField;
import org.apache.camel.component.salesforce.api.utils.QueryHelper;
import org.apache.camel.salesforce.dto.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.camel.component.salesforce.SalesforceEndpointConfig.SOBJECT_CLASS;
import static org.apache.camel.component.salesforce.SalesforceEndpointConfig.SOBJECT_QUERY;

@Slf4j
@SpringBootTest
public class SalesforceBasicRouteTest {
    @Autowired
    CamelContext context;

    @Test
    public void testSelectAccountAll() throws Exception {
        ProducerTemplate pt = context.createProducerTemplate();
        Map<String, Object> headers = Maps.newHashMap();
        headers.put(SOBJECT_CLASS, QueryRecordsAccount.class.getName());
        String fields = String.join(",", (new Account().description().getFields().stream().map(SObjectField::getName).collect(Collectors.toList())));
        String soql = "Select " + fields + " From " + Account.class.getSimpleName();
        headers.put(SOBJECT_QUERY, soql);
        QueryRecordsAccount response = pt.requestBodyAndHeaders("direct:querysf", null, headers, QueryRecordsAccount.class);
        Assert.assertNotNull(response);
        Assert.assertThat(response.getRecords().size(), Matchers.greaterThan(1));
        log.info("Response object: {}", response);
    }
    @Test
    public void testSelectContactAll() throws Exception {
        ProducerTemplate pt = context.createProducerTemplate();
        Map<String, Object> headers = Maps.newHashMap();
        headers.put(SOBJECT_CLASS, QueryRecordsContact.class.getName());
        //String fields = String.join(",", (new Contact().description().getFields().stream().map(SObjectField::getName).collect(Collectors.toList())));
        //String soql = "Select " + fields + " From " + Contact.class.getSimpleName();
        String soql = QueryHelper.queryToFetchFilteredFieldsOf(new Contact(), SObjectField::isCustom);
        headers.put(SOBJECT_QUERY, soql);
        QueryRecordsContact response = pt.requestBodyAndHeaders("direct:querysf", null, headers, QueryRecordsContact.class);
        Assert.assertNotNull(response);
        Assert.assertThat(response.getRecords().size(), Matchers.greaterThan(1));
        log.info("Response object: {}", response);
    }
    @Test
    public void testSelectUserAll() throws Exception {
        ProducerTemplate pt = context.createProducerTemplate();
        Map<String, Object> headers = Maps.newHashMap();
        headers.put(SOBJECT_CLASS, QueryRecordsUser.class.getName());
        //String fields = String.join(",", (new User().description().getFields().stream().map(SObjectField::getName).collect(Collectors.toList())));
        //String soql = "Select " + fields + " From " + Account.class.getSimpleName();
        String soql = QueryHelper.queryToFetchFilteredFieldsOf(new User(), SObjectField::isCustom);
        headers.put(SOBJECT_QUERY, soql);
        QueryRecordsUser response = pt.requestBodyAndHeaders("direct:querysf", null, headers, QueryRecordsUser.class);
        Assert.assertNotNull(response);
        Assert.assertThat(response.getRecords().size(), Matchers.greaterThan(1));
        log.info("Response object: {}", response);
    }
}
