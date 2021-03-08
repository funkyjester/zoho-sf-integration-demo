package com.funkyjester.demo.integration.model.zoho;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.funkyjester.demo.integration.model.Record;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode @ToString
public class Account extends Record {
    @JsonProperty("Owner")
    User owner;
    @JsonProperty("Ownership")
    String ownership;
    @JsonProperty("Description")
    String description;
    @JsonProperty(value = "$currency_symbol")
    String currency_symbol;
    @JsonProperty("Account_Type")
    String account_type;
    @JsonProperty("Rating")
    String rating;
    @JsonProperty("SIC_Code")
    String sic_code;
    @JsonProperty("Shipping_State")
    String shipping_state;
    @JsonProperty("$review_process")
    Review review_process;
    @JsonProperty("Website")
    String website;
    @JsonProperty("Employees")
    int employees;
    @JsonProperty("Last_Activity_Time")
    Date last_activity_time;
    @JsonProperty("Industry")
    String industry;
    @JsonProperty("Record_Image")
    String record_image;
    @JsonProperty("Modified_By")
    User modified_by;
    @JsonProperty("$review")
    String review; // is this a string or a review object?
    @JsonProperty("Account_Site")
    String account_site;
    @JsonProperty("$state")
    String state;
    @JsonProperty("$process_flow")
    boolean process_flow;
    @JsonProperty
    String phone;
    @JsonProperty
    String billing_country;
    @JsonProperty
    String account_name;
    @JsonProperty
    String account_number;
    @JsonProperty("$approved")
    boolean approved;
    @JsonProperty
    String ticker_symbol;
    @JsonProperty("$approval")
    Approval approval;
    @JsonProperty
    Date modified_time;
    @JsonProperty
    String billing_street;
    @JsonProperty
    Date created_time;
    @JsonProperty("$editable")
    boolean editable;
    @JsonProperty
    String billing_code;
    @JsonProperty("$orchestration")
    boolean orchestration;
    @JsonProperty
    String parent_account;
    @JsonProperty
    String shipping_city;
    @JsonProperty
    String shipping_country;
    @JsonProperty
    String shipping_code;
    @JsonProperty
    String billing_city;
    @JsonProperty("$in_merge")
    boolean in_merge;
    @JsonProperty
    String billing_state;
    @JsonProperty
    List<String> Tag;
    @JsonProperty
    User created_by;
    @JsonProperty
    String Fax;
    @JsonProperty
    String annual_revenue;
    @JsonProperty("$approval_state")
    String approval_state;
    @JsonProperty
    String shipping_street;
}
