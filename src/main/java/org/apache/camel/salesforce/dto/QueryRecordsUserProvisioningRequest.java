/*
 * Salesforce Query DTO generated by camel-salesforce-maven-plugin
 */
package org.apache.camel.salesforce.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.camel.component.salesforce.api.dto.AbstractQueryRecordsBase;

import java.util.List;
import javax.annotation.Generated;

/**
 * Salesforce QueryRecords DTO for type UserProvisioningRequest
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public class QueryRecordsUserProvisioningRequest extends AbstractQueryRecordsBase {

    @XStreamImplicit
    private List<UserProvisioningRequest> records;

    public List<UserProvisioningRequest> getRecords() {
        return records;
    }

    public void setRecords(List<UserProvisioningRequest> records) {
        this.records = records;
    }
}
