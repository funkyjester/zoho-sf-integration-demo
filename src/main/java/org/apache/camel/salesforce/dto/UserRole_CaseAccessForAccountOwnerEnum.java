/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist CaseAccessForAccountOwner
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum UserRole_CaseAccessForAccountOwnerEnum {

    // Edit
    EDIT("Edit"),

    // None
    NONE("None"),

    // Read
    READ("Read");


    final String value;

    private UserRole_CaseAccessForAccountOwnerEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static UserRole_CaseAccessForAccountOwnerEnum fromValue(String value) {
        for (UserRole_CaseAccessForAccountOwnerEnum e : UserRole_CaseAccessForAccountOwnerEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}