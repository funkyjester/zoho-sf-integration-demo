/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist Status
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum Contract_StatusEnum {

    // Activated
    ACTIVATED("Activated"),

    // Draft
    DRAFT("Draft"),

    // In Approval Process
    IN_APPROVAL_PROCESS("In Approval Process");


    final String value;

    private Contract_StatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Contract_StatusEnum fromValue(String value) {
        for (Contract_StatusEnum e : Contract_StatusEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}
