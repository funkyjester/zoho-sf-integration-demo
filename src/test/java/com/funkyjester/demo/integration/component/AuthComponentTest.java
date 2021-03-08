package com.funkyjester.demo.integration.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AuthComponentTest {
    @Autowired
    ZohoAPIAuthAgent zohoAPIAuthAgent;

    @Test
    public void test() {
        Assert.assertNotNull(zohoAPIAuthAgent);
    }

    @Test
    public void testInitialAuth() throws Exception {
        try {
            zohoAPIAuthAgent.doInitialAuth();
        } catch (HttpOperationFailedException e) {
            log.info("response: {}", e.getStatusText());
        }
    }
}
