package com.funkyjester.demo.integration.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funkyjester.demo.integration.config.AppCommonConfig;
import com.funkyjester.demo.integration.config.Constants;
import com.funkyjester.demo.integration.entity.ZohoWatermark;
import com.funkyjester.demo.integration.entity.persistence.ZohoWatermarkRepository;
import com.funkyjester.demo.integration.model.common.Account;
import com.funkyjester.demo.integration.model.common.Record;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.*;
import org.apache.camel.component.salesforce.api.dto.AbstractDescribedSObjectBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * exception handlers for camel routes
 */
@Slf4j
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

    /**
     * Push notifications to slack. This applies to all exception areas.
     * Attempt to send exception message, and record IDs, if available
     */
    public void sendNotification(@Body Object o, @ExchangeException Exception exept) throws Exception {
        Map<String, String> payload = new HashMap<>();
        if (o instanceof AbstractDescribedSObjectBase) {
            payload.put("salesforceid", ((AbstractDescribedSObjectBase) o).getId());
        } else if (o instanceof Record) {
            payload.put("zohoId", ((Record) o).getZohoId());
        }

        try {
            payload.put("payload", (String) o);
        } catch (Exception e) {}
        if (exept != null) {
            payload.put("error", exept.getMessage());
            payload.put("errorType", exept.getStackTrace()[0].getClassName());
            if(exept.getCause() != null) {
                payload.put("cause", exept.getCause().getMessage());
                payload.put("causeType", exept.getCause().getClass().getName());
            }
        }
        FluentProducerTemplate pt = camelContext.createFluentProducerTemplate();
        String body = getObjectMapper().writeValueAsString(payload).replace("\"", "\\\"");
        log.info("body: {}", body);
        Exchange post = pt.withBody("{\"text\": \""+body+"\"}")
                .withHeader(Exchange.HTTP_METHOD, "POST")
                .withHeader(Exchange.HTTP_URI, appCommonConfig.getSlackWebhook())
                .to(appCommonConfig.getSlackWebhook())
                .send();


    }
    @Autowired
    ZohoWatermarkRepository zohoWatermarkRepository;

    /**
     * Following failure on zoho record, roll back high-watermark to previous value.
     * This will let it retry on the next delta run.
     * @param module
     */
    public void rollBackWatermark(@Header(Constants.HEADER_RECORDTYPE) String module) {
        Optional<ZohoWatermark> byId = zohoWatermarkRepository.findById(module);
        if (byId.isPresent()) {
            zohoWatermarkRepository.save(byId.get().rollback());
        }

    }
}
