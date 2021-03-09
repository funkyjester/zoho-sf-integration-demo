package com.funkyjester.demo.integration.service;

import com.funkyjester.demo.integration.entity.ZohoWatermark;
import com.funkyjester.demo.integration.entity.persistence.ZohoWatermarkRepository;
import com.google.common.collect.Lists;
import com.zoho.crm.api.HeaderMap;
import com.zoho.crm.api.ParameterMap;
import com.zoho.crm.api.exception.SDKException;
import com.zoho.crm.api.record.Record;
import com.zoho.crm.api.record.RecordOperations;
import com.zoho.crm.api.record.ResponseHandler;
import com.zoho.crm.api.record.ResponseWrapper;
import com.zoho.crm.api.users.APIException;
import com.zoho.crm.api.users.User;
import com.zoho.crm.api.users.UsersOperations;
import com.zoho.crm.api.util.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component("zohoAPIClient")
@Slf4j
public class ZohoAPIClientImpl implements ZohoAPIClient {

    @Autowired
    ZohoWatermarkRepository zohoWatermarkRepository;



    @Override
    public List<Record> getDeals() {
        return getRecords("Deals");
    }

    @Override
    public List<Record> getUpdatedDeals() {
        return getUpdatedRecords("Deals");
    }

    @Override
    public List<Record> getAccounts() {
        return getRecords("Accounts");
    }

    @Override
    public List<Record> getUpdatedAccounts() {
        return getUpdatedRecords("Accounts");
    }

    @Override
    public List<Record> getContacts() {
        return getRecords("Contacts");
    }
    @Override
    public List<Record> getUpdatedContacts() {
        return getUpdatedRecords("Contacts");
    }

    @Override
    public List<User> getUsers() {
        return getUsers(null);
    }

    @Override
    public List<User> getUpdatedUsers() {
        String user_module = "User";
        final Optional<ZohoWatermark> watermark = zohoWatermarkRepository.findById(user_module);
        OffsetDateTime lastPoll = null;
        OffsetDateTime now = OffsetDateTime.now();
        if (watermark.isPresent()) {
            lastPoll = watermark.get().getLastPoll();
        }
        List<User> users = null;
        if (lastPoll != null) {
            users = getUsers(lastPoll);
        } else {
            users = getUsers();
        }
        if (users.size() > 0) {
            ZohoWatermark z = new ZohoWatermark();
            z.setModule(user_module);
            z.setLastPoll(now);
            zohoWatermarkRepository.save(z);
        }
        return users;
    }

    private List<Record> getUpdatedRecords(String module) {
        final Optional<ZohoWatermark> watermark = zohoWatermarkRepository.findById(module);
        OffsetDateTime lastPoll = null;
        OffsetDateTime now = OffsetDateTime.now();
        if (watermark.isPresent()) {
            lastPoll = watermark.get().getLastPoll();
        }
        List<Record> records = null;
        if (lastPoll != null) {
            records = getRecords(module, lastPoll);
        } else {
            records = getRecords(module);
        }
        if (records.size() > 0) {
            ZohoWatermark z = new ZohoWatermark();
            z.setModule(module);
            z.setLastPoll(now);
            zohoWatermarkRepository.save(z);
        }
        return records;
    }

    public List<User> getUsers(OffsetDateTime since) {
        try {
            final UsersOperations usersOperations = new UsersOperations();
            HeaderMap h = new HeaderMap();
            if (since != null) {
                h.add(RecordOperations.GetRecordsHeader.IF_MODIFIED_SINCE, since);
            }
            final APIResponse<com.zoho.crm.api.users.ResponseHandler> users = usersOperations.getUsers(new ParameterMap(), h);
            if (users.isExpected()) {
                final com.zoho.crm.api.users.ResponseHandler responseHandler = users.getObject();
                if (responseHandler instanceof com.zoho.crm.api.users.ResponseWrapper) {
                    final com.zoho.crm.api.users.ResponseWrapper responseWrapper = (com.zoho.crm.api.users.ResponseWrapper) responseHandler;
                    return responseWrapper.getUsers();
                } else if (responseHandler instanceof APIException) {
                    // TODO: log api exception
                }
            }
        } catch (SDKException e) {
            log.error("SDK Exception on users", e);
        }
        return Lists.newArrayList();
    }

    private List<Record> getRecords(String moduleAPI) {
        return getRecords(moduleAPI, null);
    }

    private List<Record> getRecords(String moduleAPI, OffsetDateTime since) {
        try {
            final RecordOperations recordOperations = new RecordOperations();
            ParameterMap parameterMap = new ParameterMap();
            parameterMap.add(RecordOperations.GetRecordParam.APPROVED, "both");
            HeaderMap h = new HeaderMap();
            if (since != null) {
                h.add(RecordOperations.GetRecordsHeader.IF_MODIFIED_SINCE, since);
            }
            APIResponse<ResponseHandler> response = recordOperations.getRecords(moduleAPI, parameterMap, h);
            if (response != null) {
                if (response.isExpected()) {
                    final ResponseHandler responseHandler = response.getObject();
                    if (responseHandler instanceof ResponseWrapper) {
                        final ResponseWrapper responseWrapper = (ResponseWrapper) responseHandler;
                        List<Record> records = responseWrapper.getData();
                        return records;
                    }
                }
            }
        } catch (SDKException sdkException) {
            log.error("SDK Exception on " + moduleAPI, sdkException);
            // TODO: handle me
        }
        return Lists.newArrayList();
    }
}
