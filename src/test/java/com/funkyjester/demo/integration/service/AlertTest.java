package com.funkyjester.demo.integration.service;

import com.funkyjester.demo.integration.component.ExceptionNotifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlertTest {
    @Autowired
    ExceptionNotifier exceptionNotifier;

    @Test
    public void testSlackOut() throws Exception {
        exceptionNotifier.sendNotification("hello world!", null);
    }
}
