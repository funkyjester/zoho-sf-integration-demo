package com.funkyjester.demo.integration.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.funkyjester.demo.integration.convert.SimpleOffsetDateTimeDeSerializer;
import com.funkyjester.demo.integration.convert.SimpleOffsetDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account extends Record {
    @JsonProperty
    String accountName;
    @JsonProperty
    Long accountNumber;
    @JsonProperty
    String accountType;
    @JsonProperty
    Double annualRevenue;
    @JsonProperty
    String currencySymbol;
    @JsonProperty
    String billingCity;
    @JsonProperty
    String billingCode;
    @JsonProperty
    String billingCountry;
    @JsonProperty
    String billingState;
    @JsonProperty
    String billingStreet;

    @JsonProperty
    Integer employeeCount;
    @JsonProperty
    String fax;

    //@JsonProperty
    //Account parentAccount;
    @JsonProperty
    String phone;
    @JsonProperty
    String rating;
    @JsonProperty
    String SICCode;

    @JsonProperty
    String shippingCity;
    @JsonProperty
    String shippingCode;
    @JsonProperty
    String shippingCountry;
    @JsonProperty
    String shippingState;
    @JsonProperty
    String shippingStreet;

    @JsonProperty
    String tickerSymbol;

    @JsonProperty
    String website;

    @JsonProperty
    User owner;
    @JsonProperty
    String ownership;

    @JsonProperty
    @JsonSerialize(converter = SimpleOffsetDateTimeSerializer.class)
    @JsonDeserialize(converter = SimpleOffsetDateTimeDeSerializer.class)
    OffsetDateTime lastActivityTime;

    @JsonProperty
    String description;

    @JsonProperty
    String industry;
}
