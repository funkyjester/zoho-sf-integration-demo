package com.funkyjester.demo.integration.convert.datamap;

import com.google.common.collect.ImmutableMap;
import org.apache.camel.salesforce.dto.Contact_LeadSourceEnum;
import org.apache.camel.salesforce.dto.Contact_LeadSourceEnum;

import java.util.Map;

public class Contact_LeadSrc {
    static Contact_LeadSourceEnum DEFAULT = Contact_LeadSourceEnum.OTHER;
    final static Map map = ImmutableMap.builder()
            .put("Cold Call", Contact_LeadSourceEnum.PHONE_INQUIRY)
            .put("Partner", Contact_LeadSourceEnum.PARTNER_REFERRAL)
            .put("Web Download", Contact_LeadSourceEnum.WEB)
            .put("Web Research", Contact_LeadSourceEnum.WEB)
            .put("Web Cases", Contact_LeadSourceEnum.WEB)
            .put("Seminar Partner", Contact_LeadSourceEnum.PARTNER_REFERRAL)
            .put("Employee Referral", Contact_LeadSourceEnum.PARTNER_REFERRAL)
            .put("External Referral", Contact_LeadSourceEnum.PARTNER_REFERRAL)
            .build();

    public static Contact_LeadSourceEnum match(String s) {
        if (s != null && map.containsKey(s)) {
            return (Contact_LeadSourceEnum)map.get(s);
        }
        return DEFAULT;
    }
}
