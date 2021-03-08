package com.funkyjester.demo.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.FluentProducerTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.FileCopyUtils;

import javax.ws.rs.client.SyncInvoker;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

@SpringBootTest
@Slf4j
public class ZohoRouteTest {
    @Autowired
    CamelContext camelContext;

    @Autowired
    ApplicationContext applicationContext;

    @Before
    public void doInitMockForLocal() throws Exception {
        if (Arrays.stream(applicationContext.getEnvironment().getActiveProfiles()).anyMatch(x -> "local".equals(x))) {
            final InputStream resourceAsStream = ZohoRouteTest.class.getClassLoader().getResourceAsStream("samples/Zoho/Accounts_response.json");
            final String s = FileCopyUtils.copyToString(new InputStreamReader(resourceAsStream));

            ZohoAPIClientImpl mockSvc = Mockito.mock(ZohoAPIClientImpl.class, Mockito.withSettings().lenient());
            Mockito.when(mockSvc)
            SyncInvoker mockInvoker = Mockito.mock(SyncInvoker.class);
            Mockito.when(mockInvoker.get(Mockito.any(Class.class))).thenReturn(s);
        }
    }

    @Test
    public void test() {
        final FluentProducerTemplate pt = camelContext.createFluentProducerTemplate();
        final Object response = pt.to("direct:foo").request();
        Assert.assertNotNull(response);
        log.info("response: {}", response);
    }
}
