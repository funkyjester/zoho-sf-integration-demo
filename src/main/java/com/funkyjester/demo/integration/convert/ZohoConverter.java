package com.funkyjester.demo.integration.convert;

import com.funkyjester.demo.integration.model.Action;
import com.funkyjester.demo.integration.model.SOR;
import com.funkyjester.demo.integration.model.common.Account;
import com.funkyjester.demo.integration.model.common.Contact;
import com.funkyjester.demo.integration.model.common.User;
import com.zoho.crm.api.record.Record;
import com.zoho.crm.api.util.Choice;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * converters between master data and zoho model
 */
@Component
@Slf4j
public class ZohoConverter implements TypeConverters {
    public static final String ACCOUNT_IDENTIFIER_KEY = "Account_Number";
    public static final String CONTACT_IDENTIFIER_KEY = "Lead_Source";

    public List<Account> convertToAccountList(List<Record> rList) {
        List<Account> list = new ArrayList<>();
        if (rList != null) {
            for (Record r : rList) {
                list.add(convertToAccount(r));
            }
        }
        return list;
    }

    @Converter(allowNull = true)
    public Account convertToAccount(Record r) {
        Account a = new Account();
        // TODO: better null handling
        if (r != null && r.getKeyValues().containsKey(ACCOUNT_IDENTIFIER_KEY)) {
            copyRecordFields(r, a);
            a.setAction(Action.UPDATE);
            a.setAccountName((String)r.getKeyValue("Account_Name"));
            a.setAccountNumber((Long) r.getKeyValue("Account_Number"));
            a.setAccountType(getChoiceValue(r.getKeyValue("Account_Type")));
            a.setAnnualRevenue((Double)r.getKeyValue("Annual_Revenue"));
            a.setCurrencySymbol((String)r.getKeyValue("$currency_symbol"));
            a.setBillingCity((String)r.getKeyValue("Billing_City"));
            a.setBillingCode((String)r.getKeyValue("Billing_Code"));
            a.setBillingCountry((String)r.getKeyValue("Billing_Country"));
            a.setBillingState((String)r.getKeyValue("Billing_State"));
            a.setBillingStreet((String)r.getKeyValue("Billing_Street"));
            a.setDescription((String)r.getKeyValue("Description"));
            a.setEmployeeCount((Integer) r.getKeyValue("Employees"));
            a.setFax((String)r.getKeyValue("Fax"));
            a.setLastActivityTime((OffsetDateTime) r.getKeyValue("Last_Activity_Time"));
            a.setOwner(convertToUser((com.zoho.crm.api.users.User)r.getKeyValue("Owner")));
            a.setOwnership(getChoiceValue(r.getKeyValue("Ownership")));
            // TODO parentAccount is same class? Check for self-ref
            //a.setParentAccount((String)r.getKeyValue("Parent_Account"));
            a.setPhone((String)r.getKeyValue("Phone"));
            a.setRating(getChoiceValue(r.getKeyValue("Rating")));
            a.setSICCode((String)r.getKeyValue("SIC_Code"));
            a.setWebsite((String)r.getKeyValue("Website"));
            a.setTickerSymbol((String)r.getKeyValue("Ticker_Symbol"));
            a.setShippingCity((String)r.getKeyValue("Shipping_City"));
            a.setShippingCode((String)r.getKeyValue("Shipping_Code"));
            a.setShippingCountry((String)r.getKeyValue("Shipping_Country"));
            a.setShippingState((String)r.getKeyValue("Shipping_State"));
            a.setShippingStreet((String)r.getKeyValue("Shipping_Street"));
            a.setIndustry(getChoiceValue(r.getKeyValue("Industry")));
            return a;
        }
        return null;
    }

    public List<Contact> convertToContactList(List<Record> rList) {
        List<Contact> list = new ArrayList<>();
        if (rList != null) {
            for (Record r : rList) {
                list.add(convertToContact(r));
            }
        }
        return list;
    }
    @Converter(allowNull = true)
    public Contact convertToContact(Record r) {
        Contact c = new Contact();
        if (r != null && r.getKeyValues().containsKey(CONTACT_IDENTIFIER_KEY)) {
            copyRecordFields(r, c);
            c.setAction(Action.UPDATE);
            c.setAccountName(convertToAccount((Record) r.getKeyValue("Account_Name")));
            c.setAssistant((String)r.getKeyValue("Assistant"));
            c.setAsstPhone((String)r.getKeyValue("Asst_Phone"));
            c.setDob((LocalDate)(r.getKeyValue("Date_of_Birth")));
            c.setDepartment((String)r.getKeyValue("Department"));
            c.setEmail((String)r.getKeyValue("Email"));
            c.setEmailOptOut((Boolean)r.getKeyValue("Email_Opt_Out"));
            c.setSecondaryEmail((String)r.getKeyValue("Secondary_Email"));
            c.setFax((String)r.getKeyValue("Fax"));
            c.setFirstName((String)r.getKeyValue("First_Name"));
            c.setFullName((String)r.getKeyValue("Full_Name"));
            c.setLastName((String)r.getKeyValue("Last_Name"));
            c.setSalutation((String)r.getKeyValue("Salutation"));
            c.setTitle((String)r.getKeyValue("Title"));
            c.setSkypeId((String)r.getKeyValue("Skype_ID"));
            c.setTwitter((String)r.getKeyValue("Twitter"));
            //c.setVendorName((String)r.getKeyValue(""));
            c.setHomePhone((String)r.getKeyValue("Home_Phone"));
            c.setMobilePhone((String)r.getKeyValue("Mobile"));
            c.setPhone((String)r.getKeyValue("Phone"));
            c.setMailingCity((String)r.getKeyValue("Mailing_City"));
            c.setMailingCountry((String)r.getKeyValue("Mailing_County"));
            c.setMailingState((String)r.getKeyValue("Mailing_State"));
            c.setMailingStreet((String)r.getKeyValue("Mailing_Street"));
            c.setMailingZip((String)r.getKeyValue("Mailing_Zip"));
            c.setOtherCity((String)r.getKeyValue("Other_City"));
            c.setOtherCode((String)r.getKeyValue("Other_Code"));
            c.setOtherCountry((String)r.getKeyValue("Other_Country"));
            c.setOtherState((String)r.getKeyValue("Other_State"));
            c.setOtherStreet((String)r.getKeyValue("Other_Street"));
            c.setOtherZip((String)r.getKeyValue("Other_Zip"));
            c.setOwner(convertToUser((com.zoho.crm.api.users.User)r.getKeyValue("Owner")));
            c.setLastActivityTime((OffsetDateTime) r.getKeyValue("Last_Activity_Time"));
            c.setDescription((String)r.getKeyValue("Description"));
            c.setLeadSource(getChoiceValue(r.getKeyValue("Lead_Source")));
            Object reporting_to = r.getKeyValue("Reporting_To");
            if (reporting_to != null && reporting_to instanceof Record) {
                c.setReportingToId(Long.toString(((Record)reporting_to).getId()));
            }
            return c;
        }
        return null;
    }

    public List<User> convertToUserList(List<com.zoho.crm.api.users.User> userList) {
        List<User> list = new ArrayList<>();
        if (userList != null) {
            for (com.zoho.crm.api.users.User user : userList) {
                list.add(convertToUser(user));
            }
        }
        return list;
    }

    @Converter(allowNull = true)
    public User convertToUser(com.zoho.crm.api.users.User in) {
        User u = new User();
        if (in != null) {
            copyRecordFields(in, u);
            u.setAlias(in.getAlias());
            u.setConfirm(in.getConfirm());
            u.setEmail(in.getEmail());
            u.setFirstName(in.getFirstName());
            u.setLastName(in.getLastName());
            u.setFullName(in.getFullName());
            return u;
        }
        return null;
    }
    private void copyRecordFields(Record from,
                                         com.funkyjester.demo.integration.model.common.Record to) {
        if (from != null && to != null) {
            to.setZohoId(Long.toString(from.getId()));
            to.setCreatedBy(convertToUser(from.getCreatedBy()));
            to.setCreateTime(from.getCreatedTime());
            to.setModifiedBy(convertToUser(from.getModifiedBy()));
            to.setModifiedTime(from.getModifiedTime());
            to.setAction(Action.IGNORE);
            to.setSor(SOR.ZOHO);
        }
    }

    private String getChoiceValue(Object v) {
        if (v != null && v instanceof Choice) {
            return ((Choice)v).getValue().toString();
        }
        return null;
    }
}
