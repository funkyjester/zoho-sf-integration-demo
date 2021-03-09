package com.funkyjester.demo.integration.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Slf4j

@SpringBootTest
public class MarshallTest {
    @Test
    public void testMarshallAccount() throws Exception {
        log.info("Current dir: {}", new File(".").getAbsolutePath());
        final List<String> strings = Files.readAllLines(new File("src/test/resources/samples/Zoho/Accounts_response.json").toPath());
        log.info("{} lines read", strings.size());
        String data = String.join(System.lineSeparator(), strings);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    }
}
