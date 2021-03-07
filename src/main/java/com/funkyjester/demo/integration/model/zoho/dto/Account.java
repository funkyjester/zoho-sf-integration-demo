package com.funkyjester.demo.integration.model.zoho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.funkyjester.demo.integration.model.Record;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Account extends Record {
    User Owner;
    String Ownership;
    String Description;
    @JsonProperty("$currency_symbol")
    String currency_symbol;
    String Account_Type;
    String Rating;
    String SIC_Code;
    String Shipping_State;
    @JsonProperty("$review_process")
    Review review_process;
    String Website;
    int Employees;
    Date Last_Activity_Time;
    String Industry;
    String Record_Image;
    User Modified_By;
    @JsonProperty("$review")
    String review; // is this a string or a review object?
    String Account_Site;
    @JsonProperty("$state")
    String state;
    @JsonProperty("$process_flow")
    boolean process_flow;
    String Phone;
    String Billing_Country;
    String Account_Name;
    // provided "id": "4816515000000307138",
    String Account_Number;
    @JsonProperty("$approved")
    boolean approved;
    String Ticker_Symbol;
    @JsonProperty("$approval")
    Approval approval;
    Date Modified_Time;
    String Billing_Street;
    Date Created_Time;
    @JsonProperty("$editable")
    boolean editable;
    String Billing_Code;
    @JsonProperty("$orchestration")
    boolean orchestration;
    String Parent_Account;
    String Shipping_City;
    String Shipping_Country;
    String Shipping_Code;
    String Billing_City;
    @JsonProperty("$in_merge")
    boolean in_merge;
    String Billing_State;
    String[] Tag;
    User Created_By;
    String Fax;
    String Annual_Revenue;
    @JsonProperty("$approval_state")
    String approval_state;
    String Shipping_Street;
}
