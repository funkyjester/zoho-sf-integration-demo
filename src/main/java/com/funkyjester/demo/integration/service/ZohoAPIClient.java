package com.funkyjester.demo.integration.service;

import com.funkyjester.demo.integration.model.zoho.Accounts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Service Implementation
 */
@Path("${zoho.api.rootpath}")
@Produces(MediaType.APPLICATION_JSON)
public interface ZohoAPIClient {

    @GET
    @Path(value = "/Accounts")
    public Accounts getAccounts();
}
