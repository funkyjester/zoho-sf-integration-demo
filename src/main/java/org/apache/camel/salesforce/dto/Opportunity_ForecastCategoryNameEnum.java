/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist ForecastCategoryName
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum Opportunity_ForecastCategoryNameEnum {

    // Best Case
    BEST_CASE("Best Case"),

    // Closed
    CLOSED("Closed"),

    // Commit
    COMMIT("Commit"),

    // Omitted
    OMITTED("Omitted"),

    // Pipeline
    PIPELINE("Pipeline");


    final String value;

    private Opportunity_ForecastCategoryNameEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Opportunity_ForecastCategoryNameEnum fromValue(String value) {
        for (Opportunity_ForecastCategoryNameEnum e : Opportunity_ForecastCategoryNameEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}
