package com.funkyjester.demo.integration.service;

import com.funkyjester.demo.integration.config.SalesforceContext;
import com.funkyjester.demo.integration.entity.SFAuditEntry;
import com.funkyjester.demo.integration.entity.persistence.SFAuditRepository;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.*;
import org.apache.camel.component.salesforce.api.dto.AbstractDescribedSObjectBase;
import org.apache.camel.component.salesforce.api.dto.CreateSObjectResult;
import org.apache.camel.component.salesforce.api.dto.RestError;
import org.apache.camel.component.salesforce.api.dto.SObjectField;
import org.apache.camel.component.salesforce.api.utils.QueryHelper;
import org.apache.camel.salesforce.dto.Account;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.camel.component.salesforce.SalesforceEndpointConfig.*;
import static org.apache.camel.salesforce.dto.DandBCompany_GeoCodeAccuracyEnum.T;

/**
 * service representation of salesforce api
 * Allows upserts and queries
 * Not implemented: deletes
 */
@Component("salesforceClient")
@Slf4j
public class SalesforceClientImpl implements SalesforceClient {
    public static final String ZOHO_ID = "zohoId";
    public static final String ZOHO_ID_C = "zohoId__c";
    @Autowired
    CamelContext camelContext;
    @Autowired
    SalesforceContext salesforceContext;

    @Override
    public CreateSObjectResult upsertRecord(
            @Header("SOR") String sourceOfRecord,
            @Header("Action") String action,
            // TODO behavior on action
            @Body AbstractDescribedSObjectBase record) {
        if (record != null) {
            Map<String, Object> headers = new HashMap<>();
            ProducerTemplate pt = camelContext.createProducerTemplate();
            headers.put(FORMAT, salesforceContext.getSalesforceResponseFormat());
            headers.put(SOBJECT_NAME, record.getClass().getSimpleName());
            // check for zohoId in record
            try {
                record.getClass().getDeclaredField(ZOHO_ID_C);
                //log.info("{} exists on {}", ZOHO_ID_C, record.getClass().getSimpleName());
                headers.put(SOBJECT_EXT_ID_NAME, ZOHO_ID_C);
            } catch (NoSuchFieldException nsfe) {
                // ignore. use salesforce Id
                //log.info("{} not found on {}", ZOHO_ID_C, record.getClass().getSimpleName());
            }
            CreateSObjectResult createSObjectResult = pt.requestBodyAndHeaders("salesforce:upsertSObject", record, headers, CreateSObjectResult.class);
            auditLog(createSObjectResult);
            return createSObjectResult;
        }
        return null;
    }

    @Autowired
    SFAuditRepository auditRepository;

    private void auditLog(CreateSObjectResult obj) {
        if (obj != null) {
            SFAuditEntry entry = new SFAuditEntry();
            entry.setId(OffsetDateTime.now());
            entry.setSalesforceId(obj.getId());
            entry.setSuccess(obj.getSuccess());
            if (!obj.getSuccess() && obj.getErrors() != null && obj.getErrors().size() > 0) {
                RestError restError = obj.getErrors().get(0);
                entry.setMessage(restError.getMessage());
                entry.setErrorCode(restError.getErrorCode());
            }
            auditRepository.save(entry);
        }
    }

    @Override
    public <T> Optional<T> queryBySalesforceId(Class<T> clazz, String salesforceId) {
        if (clazz == null || salesforceId == null || Strings.isEmpty(salesforceId)) {
            return Optional.empty();
        }
        try {
            Object o = clazz.newInstance();
            if (!(o instanceof AbstractDescribedSObjectBase)) {
                return Optional.empty();
            }
            ProducerTemplate pt = camelContext.createProducerTemplate();
            Map<String, Object> headers = new HashMap<>();
            headers.put(FORMAT, salesforceContext.getSalesforceResponseFormat());
            headers.put(SOBJECT_FIELDS,
                    String.join(",", (((AbstractDescribedSObjectBase)o).description().getFields().stream().map(SObjectField::getName).collect(Collectors.toList()))));
            //headers.put(SOBJECT_FIELDS, QueryHelper.queryToFetchFilteredFieldsOf((AbstractDescribedSObjectBase)o, SObjectField::isCustom));
            headers.put(SOBJECT_NAME, clazz.getSimpleName());
            headers.put(SOBJECT_CLASS, clazz.getName());
            return Optional.ofNullable(pt.requestBodyAndHeaders("salesforce:getSObject", salesforceId, headers, clazz));
        } catch (Exception e) {
            log.error("exception", e);
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> queryByZohoId(Class<T> clazz, String zohoId) {
        if (clazz == null || zohoId == null || Strings.isEmpty(zohoId)) {
            return Optional.empty();
        }
        try {
            clazz.getDeclaredField(ZOHO_ID_C);
            //log.info("external id field found for {}", clazz.getSimpleName());
        } catch (NoSuchFieldException nsfe) {
            log.warn("no external id field found for {}", clazz.getSimpleName());
            return Optional.empty();
        }
        try {
            Object o = clazz.newInstance();
            if (!(o instanceof AbstractDescribedSObjectBase)) {
                return Optional.empty();
            }
            ProducerTemplate pt = camelContext.createProducerTemplate();
            Map<String, Object> headers = new HashMap<>();
            headers.put(FORMAT, salesforceContext.getSalesforceResponseFormat());
            headers.put(SOBJECT_FIELDS,
                    String.join(",", (((AbstractDescribedSObjectBase)o).description().getFields().stream().map(SObjectField::getName).collect(Collectors.toList()))));
            //headers.put(SOBJECT_FIELDS, QueryHelper.queryToFetchFilteredFieldsOf((AbstractDescribedSObjectBase)o, SObjectField::isCustom));
            headers.put(SOBJECT_NAME, clazz.getSimpleName());
            headers.put(SOBJECT_CLASS, clazz.getName());
            headers.put(SOBJECT_EXT_ID_NAME, ZOHO_ID_C);
            return Optional.ofNullable(pt.requestBodyAndHeaders("salesforce:getSObjectWithId", zohoId, headers, clazz));
        } catch (Exception e) {
            log.error("exception", e);
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> queryByAnyId(Class<T> clazz, String salesforceId, String zohoId) {
        if (clazz != null) {
            if (salesforceId != null) {
                return queryBySalesforceId(clazz, salesforceId);
            } else if (zohoId != null) {
                return queryByZohoId(clazz, zohoId);
            } else {
                log.warn("queryByAnyId did not find any IDs");
            }
        }
        return Optional.empty();
    }


    private Cache<String, AbstractDescribedSObjectBase> lookupCache = CacheBuilder.newBuilder()
            .build();

    // cached entry lookup. Store twice, so we can lookup using either key
    @Override
    public <T> T cachedLookup(Class<T> clazz, String sId, String zId) {
        if (clazz == null || (sId == null && zId == null)) { return null; }
        String s_only = "";
        String z_only = "";
        if (Strings.isNotEmpty(sId)) {
            s_only = clazz.getSimpleName() + "/" + sId;
        }
        if (Strings.isNotEmpty((zId))) {
            z_only = clazz.getSimpleName() + "/" + zId;
        }
        AbstractDescribedSObjectBase o = null;
        if (Strings.isNotEmpty(z_only)) {
            o = lookupCache.getIfPresent(z_only);
        }
        if (o == null && Strings.isNotEmpty(s_only)) {
            o = lookupCache.getIfPresent(s_only);
        }
        if (o == null) {
            //log.info("Cache miss");
            Optional<T> t = queryByAnyId(clazz, sId, zId);
            if (t.isPresent()) {
                o = (AbstractDescribedSObjectBase)(t.get());
                if (Strings.isNotEmpty(o.getId())) {
                    s_only = clazz.getSimpleName() + "/" + o.getId();
                    lookupCache.put(s_only, o);
                }
                if (Strings.isNotEmpty(z_only)) {
                    lookupCache.put(z_only, o);
                }
            }
        } else {
            //log.info("Cache hit");
        }
        return (T)o;
    }
}
