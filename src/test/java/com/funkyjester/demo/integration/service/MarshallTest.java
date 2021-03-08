package com.funkyjester.demo.integration.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funkyjester.demo.integration.model.zoho.PagedResponse;
import com.funkyjester.demo.integration.model.zoho.Accounts;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Slf4j
public class MarshallTest {
    @Test
    public void testMarshallAccount() throws Exception {
        log.info("Current dir: {}", new File(".").getAbsolutePath());
        final List<String> strings = Files.readAllLines(new File("src/test/resources/samples/Zoho/Accounts_response.json").toPath());
        log.info("{} lines read", strings.size());
        String data = String.join(System.lineSeparator(), strings);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        final PagedResponse pagedResponse = mapper.readValue(data, Accounts.class);

    }
}
