package com.funkyjester.demo.integration.service;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.component.salesforce.api.dto.AbstractDescribedSObjectBase;
import org.apache.camel.component.salesforce.api.dto.CreateSObjectResult;

import java.util.Optional;

public interface SalesforceClient {
    CreateSObjectResult upsertRecord(
            String sourceOfRecord,
            String action,
            AbstractDescribedSObjectBase record);

    <T> Optional<T> queryBySalesforceId(Class<T> clazz, String salesforceId);
    <T> Optional<T> queryByZohoId(Class<T> clazz, String zohoId);
    <T> Optional<T> queryByAnyId(Class<T> clazz, String salesforceId, String zohoId);
    <T> T cachedLookup(Class<T> clazz, String sId, String zId);
}
