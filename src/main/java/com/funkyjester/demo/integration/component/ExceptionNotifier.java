package com.funkyjester.demo.integration.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funkyjester.demo.integration.config.AppCommonConfig;
import com.funkyjester.demo.integration.model.common.Account;
import com.funkyjester.demo.integration.model.common.Record;
import org.apache.camel.Body;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangeException;
import org.apache.camel.component.salesforce.api.dto.AbstractDescribedSObjectBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionNotifier {
    @Autowired
    AppCommonConfig appCommonConfig;

    @Autowired
    CamelContext camelContext;

    ObjectMapper objectMapper;

    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            //objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        }
        return objectMapper;
    }
    public void sendNotification(@Body Object o, @ExchangeException Exception exept) throws Exception {
        Map<String, String> payload = new HashMap<>();
        if (o instanceof AbstractDescribedSObjectBase) {
            payload.put("id", ((AbstractDescribedSObjectBase) o).getId());
        } else if (o instanceof Record) {
            payload.put("zoho-id", ((Record) o).getZohoId());
        } else {
            payload.put("o", (String) o);
        }
        if (exept != null) {
            payload.put("error", exept.getMessage());
        }
        camelContext.createFluentProducerTemplate()
                .withBody(getObjectMapper().writeValueAsString(payload)).to(appCommonConfig.getSlackWebhook()).send();
    }
}
