/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist ApprovalStatus
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum UserProvisioningRequest_ApprovalStatusEnum {

    // Approved
    APPROVED("Approved"),

    // Denied
    DENIED("Denied"),

    // NotRequired
    NOTREQUIRED("NotRequired"),

    // Required
    REQUIRED("Required");


    final String value;

    private UserProvisioningRequest_ApprovalStatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static UserProvisioningRequest_ApprovalStatusEnum fromValue(String value) {
        for (UserProvisioningRequest_ApprovalStatusEnum e : UserProvisioningRequest_ApprovalStatusEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}