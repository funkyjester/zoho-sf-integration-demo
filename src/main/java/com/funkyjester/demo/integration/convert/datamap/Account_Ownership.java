package com.funkyjester.demo.integration.convert.datamap;

import com.google.common.collect.ImmutableMap;
import org.apache.camel.salesforce.dto.Account_OwnershipEnum;
import org.apache.camel.salesforce.dto.Account_TypeEnum;

import java.util.Map;

public class Account_Ownership {
    static Account_OwnershipEnum DEFAULT = Account_OwnershipEnum.OTHER;
    final static Map map = ImmutableMap.builder()
            .put("-None-", Account_OwnershipEnum.OTHER)
            .put("Other", Account_OwnershipEnum.OTHER)
            .put("Private", Account_OwnershipEnum.PRIVATE)
            .put("Public", Account_OwnershipEnum.PUBLIC)
            .put("Subsidiary", Account_OwnershipEnum.SUBSIDIARY)
            .put("Government", Account_OwnershipEnum.OTHER)
            .put("Partnership", Account_OwnershipEnum.OTHER)
            .put("Privately Held", Account_OwnershipEnum.PRIVATE)
            .put("Public Company", Account_OwnershipEnum.PUBLIC)
            .build();

    public static Account_OwnershipEnum match(String s) {
        if (s != null && map.containsKey(s)) {
            return (Account_OwnershipEnum)map.get(s);
        }
        return DEFAULT;
    }
}
