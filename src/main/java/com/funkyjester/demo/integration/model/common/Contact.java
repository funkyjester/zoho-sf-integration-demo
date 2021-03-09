package com.funkyjester.demo.integration.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.funkyjester.demo.integration.convert.SimpleOffsetDateTimeDeSerializer;
import com.funkyjester.demo.integration.convert.SimpleOffsetDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact extends Record {
    @JsonProperty
    Account accountName;
    @JsonProperty
    String assistant;
    @JsonProperty
    String asstPhone;
    @JsonProperty
    LocalDate dob;
    @JsonProperty
    String department;
    @JsonProperty
    String email;
    @JsonProperty
    Boolean emailOptOut;
    @JsonProperty
    String secondaryEmail;
    @JsonProperty
    String fax;

    @JsonProperty
    Contact reportingTo;

    @JsonProperty
    String firstName;
    @JsonProperty
    String fullName;
    @JsonProperty
    String lastName;
    @JsonProperty
    String salutation;
    @JsonProperty
    String title;

    @JsonProperty
    String skypeId;
    @JsonProperty
    String twitter;

    // TODO: vendors out of scope
    //@JsonProperty
    //String vendorName;

    @JsonProperty
    String homePhone;
    @JsonProperty
    String mobilePhone;
    @JsonProperty
    String phone;

    @JsonProperty
    String mailingCity;
    @JsonProperty
    String mailingCountry;
    @JsonProperty
    String mailingState;
    @JsonProperty
    String mailingStreet;
    @JsonProperty
    String mailingZip;

    @JsonProperty
    String otherCity;
    @JsonProperty
    String otherCode;
    @JsonProperty
    String otherCountry;
    @JsonProperty
    String otherState;
    @JsonProperty
    String otherStreet;
    @JsonProperty
    String otherZip;

    @JsonProperty
    User owner;

    @JsonProperty
    @JsonSerialize(converter = SimpleOffsetDateTimeSerializer.class)
    @JsonDeserialize(converter = SimpleOffsetDateTimeDeSerializer.class)
    OffsetDateTime lastActivityTime;

    @JsonProperty
    String description;

    @JsonProperty
    String leadSource;

}
