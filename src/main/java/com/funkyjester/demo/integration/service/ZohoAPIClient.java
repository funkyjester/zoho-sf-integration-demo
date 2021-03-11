package com.funkyjester.demo.integration.service;

import com.zoho.crm.api.record.Record;
import com.zoho.crm.api.users.User;

import java.util.List;

/**
 * Service Implementation
 */
//@Path("${zoho.api.rootpath}")
//@Produces(MediaType.APPLICATION_JSON)
public interface ZohoAPIClient {
    public static final String RES_USERS = "Users";
    public static final String RES_ACCOUNTS = "Accounts";
    public static final String RES_CONTACTS = "Contacts";
    public static final String RES_DEALS = "Deals";


    //@GET
    //@Path(value = "/Accounts")
    List<Record> getAccounts();

    //@GET
    //@Path(value = "/Contacts")
    List<Record> getContacts();

    //@GET
    //@Path(value = "/Users")
    List<User> getUsers();

    //@GET
    //@Path(value = "/Deals")
    List<Record> getDeals();

    List<Record> getUpdatedAccounts();

    List<Record> getUpdatedContacts();

    List<User> getUpdatedUsers();

    List<Record> getUpdatedDeals();
}
