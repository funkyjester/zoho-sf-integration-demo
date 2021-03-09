package com.funkyjester.demo.integration.convert;

import com.funkyjester.demo.integration.convert.datamap.Account_Industry;
import com.funkyjester.demo.integration.convert.datamap.Account_Ownership;
import com.funkyjester.demo.integration.convert.datamap.Account_Type;
import com.funkyjester.demo.integration.convert.datamap.Contact_LeadSrc;
import com.funkyjester.demo.integration.service.SalesforceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.apache.camel.salesforce.dto.Account;
import org.apache.camel.salesforce.dto.Contact;
import org.apache.camel.salesforce.dto.Contact_SalutationEnum;
import org.apache.camel.salesforce.dto.User;
import org.apache.camel.spi.TypeConverterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class SalesforceConverter implements TypeConverters {
    @Autowired
    SalesforceClient salesforceClient;

    @Converter(allowNull = false)
    public Account convertAccount(com.funkyjester.demo.integration.model.common.Account src) {
        Account a = new Account();
        if (src != null) {
            a.setzohoId__c(src.getZohoId());
            if (src.getSalesforceId() != null) {
                a.setId(src.getSalesforceId());
            }
            a.setAccountNumber(Long.toString(src.getAccountNumber()));
            a.setAnnualRevenue(src.getAnnualRevenue());
            //a.setAssets();
            a.setBillingCity(src.getBillingCity());
            a.setBillingCountry(src.getBillingCountry());
            a.setBillingPostalCode(src.getBillingCode());
            a.setBillingState(src.getBillingState());
            a.setBillingStreet(src.getBillingStreet());
            a.setDescription(src.getDescription());
            a.setType(Account_Type.match(src.getAccountType()));
            a.setShippingCity(src.getShippingCity());
            a.setShippingCountry(src.getShippingCountry());
            a.setShippingPostalCode(src.getShippingCode());
            a.setShippingState(src.getShippingState());
            a.setShippingStreet(src.getShippingStreet());
            a.setSic(src.getSICCode());
            a.setIndustry(Account_Industry.match(src.getIndustry()));
            a.setNumberOfEmployees(src.getEmployeeCount());
            a.setOwnership(Account_Ownership.match(src.getOwnership()));
            a.setTickerSymbol(src.getTickerSymbol());
            a.setWebsite(src.getWebsite());
            a.setFax(src.getFax());
            a.setPhone(src.getPhone());
            a.setName(src.getAccountName());
            if (src.getOwner() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getOwner().getSalesforceId(), src.getOwner().getZohoId());
                if (l != null) {
                    a.setOwnerId(l.getId());
                } else {
                    a.setOwnerId(null);
                }
            }
            if (src.getCreatedBy() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getCreatedBy().getSalesforceId(), src.getCreatedBy().getZohoId());
                if (l != null) {
                    a.setCreatedById(l.getId());
                } else {
                    a.setCreatedById(null);
                }
            }
            a.setCreatedDate(src.getCreateTime().toZonedDateTime());
            a.setLastModifiedDate(src.getModifiedTime().toZonedDateTime());
            if (src.getModifiedBy() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getModifiedBy().getSalesforceId(), src.getModifiedBy().getZohoId());
                if (l != null) {
                    a.setLastModifiedById(l.getId());
                } else {
                    a.setLastModifiedById(null);
                }
            }
            a.setLastActivityDate(src.getLastActivityTime().toZonedDateTime());
        }
        return a;
    }

    @Converter(allowNull = false)
    public Contact convertContact(com.funkyjester.demo.integration.model.common.Contact src) {
        Contact c = new Contact();
        if (src != null) {
            c.setzohoId__c(src.getZohoId());
            if (src.getSalesforceId() != null) {
                c.setId(src.getSalesforceId());
            }
            //c.setAccount(src.getAccountName().getSalesforceId()); // need to lookup
            c.setAssistantName(src.getAssistant());
            c.setAssistantPhone(src.getAsstPhone());
            if (src.getCreatedBy() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getCreatedBy().getSalesforceId(), src.getCreatedBy().getZohoId());
                if (l != null) {
                    c.setCreatedById(l.getId());
                } else {
                    c.setCreatedById(null);
                }
            }
            c.setCreatedDate(src.getCreateTime().toZonedDateTime());
            c.setLastModifiedDate(src.getModifiedTime().toZonedDateTime());
            if (src.getModifiedBy() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getModifiedBy().getSalesforceId(), src.getModifiedBy().getZohoId());
                if (l != null) {
                    c.setLastModifiedById(l.getId());
                } else {
                    c.setLastModifiedById(null);
                }
            }
            c.setLastActivityDate(src.getLastActivityTime().toZonedDateTime());
            c.setBirthdate(src.getDob());
            c.setDepartment(src.getDepartment());
            c.setDescription(src.getDescription());
            c.setEmail(src.getEmail());
            c.setFax(src.getFax());
            c.setFirstName(src.getFirstName());
            c.setLastName(src.getLastName());
            c.setName(src.getFullName());
            c.setHomePhone(src.getHomePhone());
            c.setLeadSource(Contact_LeadSrc.match(src.getLeadSource()));
            c.setMailingCity(src.getMailingCity());
            c.setMailingCountry(src.getMailingCountry());
            c.setMailingPostalCode(src.getMailingZip());
            c.setMailingState(src.getMailingState());
            c.setMailingStreet(src.getMailingStreet());
            c.setMobilePhone(src.getMobilePhone());
            c.setOtherCity(src.getOtherCity());
            c.setOtherCountry(src.getOtherCountry());
            c.setOtherPostalCode(src.getOtherZip());
            c.setOtherState(src.getOtherState());
            c.setOtherStreet(src.getOtherStreet());
            if (src.getOwner() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getOwner().getSalesforceId(), src.getOwner().getZohoId());
                if (l != null) {
                    c.setOwnerId(l.getId());
                } else {
                    c.setOwnerId(null);
                }
            }
            if (src.getReportingTo() != null) {
                Contact l = salesforceClient.cachedLookup(Contact.class, src.getReportingTo().getSalesforceId(), src.getReportingTo().getZohoId());
                if (l != null) {
                    c.setReportsToId(l.getId());
                } else {
                    c.setReportsToId(null);
                }
            }
            //c.setReportsTo(src.getReportsTo);
            c.setSalutation(Contact_SalutationEnum.fromValue(src.getSalutation())); // probably better with datamap
            c.setTitle(src.getTitle());
        }
        return c;
    }

    @Converter(allowNull = false)
    public User convertUser(com.funkyjester.demo.integration.model.common.User src) {
        User u = new User();
        if (src != null) {
            u.setzohoId__c(src.getZohoId());
            if (src.getSalesforceId() != null) {
                u.setId(src.getSalesforceId());
            }
            if (src.getCreatedBy() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getCreatedBy().getSalesforceId(), src.getCreatedBy().getZohoId());
                if (l != null) {
                    u.setCreatedById(l.getId());
                } else {
                    u.setCreatedById(null);
                }
            }
            u.setCreatedDate(src.getCreateTime().toZonedDateTime());
            if (src.getModifiedBy() != null) {
                User l = salesforceClient.cachedLookup(User.class, src.getModifiedBy().getSalesforceId(), src.getModifiedBy().getZohoId());
                if (l != null) {
                    u.setLastModifiedById(l.getId());
                } else {
                    u.setLastModifiedById(null);
                }
            }
            u.setLastModifiedDate(src.getModifiedTime().toZonedDateTime());
            u.setAlias(src.getAlias());
            u.setEmail(src.getEmail());
            u.setFirstName(src.getFirstName());
            u.setLastName(src.getLastName());
            u.setName(src.getFullName());
        }
        return u;
    }

}
