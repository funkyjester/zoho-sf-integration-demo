package com.funkyjester.demo.integration.service;

import com.funkyjester.demo.integration.model.Action;
import com.funkyjester.demo.integration.model.SOR;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.salesforce.dto.Account;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Slf4j
public class SalesforceServiceTest {
    @Autowired
    SalesforceClient salesforceClient;

    @Test
    public void test() {
        Assert.assertNotNull(salesforceClient);
    }
    // fetch using both IDs. Ensure they are the same
    @Test
    public void testQuery() {
        Optional<Account> account_opt = salesforceClient.queryByAnyId(Account.class, null, "4816515000000307132");
        Assert.assertTrue(account_opt.isPresent());
        Account account = account_opt.get();
        String sfId = account.getId();
        log.info("sfId: {}", sfId);
        Optional<Account> sf_query_opt = salesforceClient.queryByAnyId(Account.class, sfId, null);
        Assert.assertTrue(sf_query_opt.isPresent());
        Assert.assertTrue(account.getId().equals(sf_query_opt.get().getId()));
        Assert.assertTrue(account.getzohoId__c().equals(sf_query_opt.get().getzohoId__c()));
    }

    @Test
    public void queryUpdateQueryDelta() {
        Account a = salesforceClient.queryByAnyId(Account.class, null, "4816515000000307132").get();
        Assume.assumeTrue(Strings.isNotEmpty(a.getWebsite()));
        String website = a.getWebsite();
        String newWebSite = website + "/xyz";
        a.setWebsite(newWebSite);
        // work around. Probably permission in salesforce for these field, which is failing this test
        a.setLastViewedDate(null);
        a.setLastReferencedDate(null);
        a.setBillingAddress(null);
        a.setPhotoUrl(null);
        salesforceClient.upsertRecord(SOR.SALESFORCE.toString(), Action.UPDATE.toString(), a);

        Account b = salesforceClient.queryByAnyId(Account.class, null, "4816515000000307132").get();
        Assert.assertTrue(Strings.isNotEmpty(b.getWebsite()));
        Assert.assertFalse((website.equals(b.getWebsite())));
        // cleanup
        a.setWebsite(website);
        a.setLastViewedDate(null);
        a.setLastReferencedDate(null);
        a.setBillingAddress(null);
        a.setPhotoUrl(null);
        salesforceClient.upsertRecord(SOR.SALESFORCE.toString(), Action.UPDATE.toString(), a);
    }


}
