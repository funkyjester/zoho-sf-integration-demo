package com.funkyjester.demo.integration.controller;

import com.funkyjester.demo.integration.entity.persistence.ZohoWatermarkRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * expose rest endpoints for various management functionality
 * WARNING: these are currently unsecured
 */
@Slf4j
@RestController
@RequestMapping("/service_entrance")
public class ServiceController {
    @Autowired
    ZohoWatermarkRepository jpaRepo;
    @Autowired
    CamelContext camelContext;

    /**
     * delete high watermark records used on ZohoCRM side for change delta checks
     * @return
     */
    @GetMapping("/flush_watermark")
    public String flushWatermarks() {
        jpaRepo.deleteAll();
        log.warn("Watermarks flushed from service controller");
        return "OK";
    }

    /**
     * trigger full load on zoho module
     */
    @GetMapping("/trigger_load/{module}")
    public String forceLoad(@PathVariable("module") String module) {
        String ep_template = "jms:topic:crm.event.__MODULE__.all?subscriptionDurable=false";
        ArrayList<String> ok_modules = Lists.newArrayList("user", "account", "contact");
        if (Strings.isNotEmpty(module)) {
            if (ok_modules.contains(module)) {
                String ep = ep_template.replaceAll("__MODULE__", module);
                camelContext.createProducerTemplate().sendBody(ep, null);
                return "OK";
            }
        }
        return "NOT OK";
    }
}
