package com.funkyjester.demo.integration.convert.datamap;

import com.google.common.collect.ImmutableMap;
import org.apache.camel.salesforce.dto.Account_IndustryEnum;

import java.util.Map;

public class Account_Industry {
    static Account_IndustryEnum DEFAULT = Account_IndustryEnum.OTHER;
    final static Map map = ImmutableMap.builder()
            .put("-None-", Account_IndustryEnum.OTHER)
            .put("ASP (Application Service Provider)", Account_IndustryEnum.CONSULTING)
            .put("Data/Telecom OEM", Account_IndustryEnum.TELECOMMUNICATIONS)
            .put("ERP (Enterprise Resource Planning)", Account_IndustryEnum.CONSULTING)
            .put("Government/Military", Account_IndustryEnum.GOVERNMENT)
            .put("Large Enterprise", Account_IndustryEnum.OTHER)
            .put("ManagementISV", Account_IndustryEnum.CONSULTING)
            .put("MSP (Management Service Provider", Account_IndustryEnum.CONSULTING)
            .put("Network Equipment Enterprise", Account_IndustryEnum.TELECOMMUNICATIONS)
            .put("Non-management ISV", Account_IndustryEnum.CONSULTING)
            .put("Optical Networking", Account_IndustryEnum.TELECOMMUNICATIONS)
            .put("Service Provider", Account_IndustryEnum.CONSULTING)
            .put("Small/Medium Enterprise", Account_IndustryEnum.OTHER)
            .put("Storage Equipment", Account_IndustryEnum.MANUFACTURING)
            .put("Storage Service Provider", Account_IndustryEnum.OTHER)
            .put("Systems Integrator", Account_IndustryEnum.CONSULTING)
            .put("Wireless Industry", Account_IndustryEnum.TELECOMMUNICATIONS)
            .put("Communications", Account_IndustryEnum.TELECOMMUNICATIONS)
            .put("Education", Account_IndustryEnum.EDUCATION)
            .put("Financial Services", Account_IndustryEnum.BANKING)
            .put("Manufacturing", Account_IndustryEnum.MANUFACTURING)
            .put("Real Estate", Account_IndustryEnum.OTHER)
            .put("Technology", Account_IndustryEnum.TECHNOLOGY)
            .build();

    public static Account_IndustryEnum match(String s) {
        if (s != null && map.containsKey(s)) {
            return (Account_IndustryEnum) map.get(s);
        }
        return DEFAULT;
    }
}
