package com.funkyjester.demo.integration.convert.datamap;

import com.google.common.collect.ImmutableMap;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.camel.salesforce.dto.Account_TypeEnum;

import java.util.Map;

public class Account_Type {
    static Account_TypeEnum DEFAULT = Account_TypeEnum.OTHER;
    final static Map map = ImmutableMap.builder()
            .put("Vendor", Account_TypeEnum.OTHER)
            .put("Supplier", Account_TypeEnum.OTHER)
            .put("Reseller", Account_TypeEnum.CHANNEL_PARTNER___RESELLER)
            .put("Prospect", Account_TypeEnum.PROSPECT)
            .put("Press", Account_TypeEnum.OTHER)
            .put("Partner", Account_TypeEnum.CHANNEL_PARTNER___RESELLER)
            .put("Other", Account_TypeEnum.OTHER)
            .put("Investor", Account_TypeEnum.OTHER)
            .put("Integrator", Account_TypeEnum.TECHNOLOGY_PARTNER)
            .put("Distributor", Account_TypeEnum.OTHER)
            .put("Customer", Account_TypeEnum.CUSTOMER___DIRECT)
            .put("Competitor", Account_TypeEnum.OTHER)
            .put("Analyst", Account_TypeEnum.TECHNOLOGY_PARTNER)
            .put("-None-", Account_TypeEnum.OTHER)
            .build();

    public static Account_TypeEnum match(String s) {
        if (s != null && map.containsKey(s)) {
            return (Account_TypeEnum)map.get(s);
        }
        return DEFAULT;
    }
}
