package com.funkyjester.demo.integration.service;

import com.zoho.crm.api.record.Record;
import com.zoho.crm.api.tags.Tag;
import com.zoho.crm.api.users.User;
import com.zoho.crm.api.util.Choice;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.FluentProducerTemplate;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ZohoServiceTest {

    @Autowired
    ZohoAPIClient zohoAPIClient;

    @Autowired
    CamelContext camelContext;

    @Test
    public void testServiceCallAccount() throws Exception {
        Assert.assertNotNull(zohoAPIClient);
        log.info("---- Accounts ----");
        final List<Record> accounts = zohoAPIClient.getAccounts();
        dumpRecord(accounts);
        log.info("---- Contacts ----");
        final List<Record> contacts = zohoAPIClient.getContacts();
        dumpRecord(contacts);
        log.info("---- Deals ----");
        dumpRecord(zohoAPIClient.getDeals());
        log.info("---- Users ----");
        final List<User> users = zohoAPIClient.getUsers();
        dumpUser(users);
    }

    private void dumpRecord(List<Record> records) {
        for (Record entry : records) {
            log.info("entry: {}", entry.getId());
            for (String k : entry.getKeyValues().keySet()) {
                String className = "null";
                if (entry.getKeyValue(k) != null) {
                    className = entry.getKeyValue(k).getClass().getSimpleName();
                }
                log.info("  {} -> {} ({})", k, entry.getKeyValue(k), className);
                if ("Choice".equals(className)) {
                    final Choice keyValue = (Choice) entry.getKeyValue(k);
                    log.info("      {}", keyValue.getValue());
                }
            }
            for (Tag tag : entry.getTag()) {
                log.info("  tag: {}", tag.getName());
            }
        }
    }

    private void dumpUser(List<User> users) {
        if (users == null) return;
        for (User entry : users) {
            if (entry == null) continue;
            log.info("entry: {}", entry.getId());
            for (String k : entry.getKeyValues().keySet()) {
                String className = "null";
                if (entry.getKeyValue(k) != null) {
                    className = entry.getKeyValue(k).getClass().getSimpleName();
                }
                log.info("  {} -> {} ({})", k, entry.getKeyValue(k), className);
                if ("Choice".equals(className)) {
                    final Choice keyValue = (Choice) entry.getKeyValue(k);
                    log.info("      {}", keyValue.getValue());
                }
            }
        }
    }

}
