package com.funkyjester.demo.integration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funkyjester.demo.integration.component.ZohoAPIAuthAgent;
import com.funkyjester.demo.integration.model.zoho.Accounts;
import com.funkyjester.demo.integration.model.zoho.PagedResponse;
import com.google.j2objc.annotations.AutoreleasePool;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Component("zohoAPIClient")
@Slf4j
public class ZohoAPIClientImpl implements ZohoAPIClient {
    @Autowired
    WebTarget zohoClient;

    @Autowired
    ZohoAPIAuthAgent zohoAPIAuthAgent;

    ObjectMapper objectMapper;

    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        }
        return objectMapper;
    }

    @Override
    public Accounts getAccounts() {
        try {
            String response = tryGet("/Accounts");
            log.info("Response: {}", response);
            final Accounts accounts = getObjectMapper().readValue(response, Accounts.class);
            return accounts;
        } catch (JsonProcessingException jpe) {
            log.error("Error processing", jpe);
        } catch (Exception e) {
            log.error("Unknown exception", e);
        }
        return new Accounts();
    }

    private String tryGet(String path) throws Exception {
        int retries = 1;
        boolean retryableError = false;
        do {
            try {
                String response = zohoClient
                        .path(path)
                        .request(MediaType.APPLICATION_JSON)
                        //.header("Authentication", zohoAPIAuthAgent.getCurrentAuthHeader())
                        .get(String.class);
                return response;
            } catch (NotAuthorizedException nre) {
                try {
                    zohoAPIAuthAgent.doRefreshAuth();
                    retryableError = true;
                } catch (Exception e) {
                    log.error("Failed to refresh authentication", e);
                }
            }
        } while (retryableError && retries-- > 0);
        return null;
    }
}
