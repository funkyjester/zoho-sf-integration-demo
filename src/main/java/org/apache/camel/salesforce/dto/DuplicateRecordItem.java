/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin.
 */
package org.apache.camel.salesforce.dto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import org.apache.camel.component.salesforce.api.dto.AbstractDescribedSObjectBase;
import org.apache.camel.component.salesforce.api.dto.Attributes;
import org.apache.camel.component.salesforce.api.dto.ChildRelationShip;
import org.apache.camel.component.salesforce.api.dto.InfoUrls;
import org.apache.camel.component.salesforce.api.dto.NamedLayoutInfo;
import org.apache.camel.component.salesforce.api.dto.RecordTypeInfo;
import org.apache.camel.component.salesforce.api.dto.SObjectDescription;
import org.apache.camel.component.salesforce.api.dto.SObjectDescriptionUrls;
import org.apache.camel.component.salesforce.api.dto.SObjectField;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Salesforce DTO for SObject DuplicateRecordItem
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
@XStreamAlias("DuplicateRecordItem")
public class DuplicateRecordItem extends AbstractDescribedSObjectBase {

    public DuplicateRecordItem() {
        getAttributes().setType("DuplicateRecordItem");
    }

    private static final SObjectDescription DESCRIPTION = createSObjectDescription();

    private String DuplicateRecordSetId;

    @JsonProperty("DuplicateRecordSetId")
    public String getDuplicateRecordSetId() {
        return this.DuplicateRecordSetId;
    }

    @JsonProperty("DuplicateRecordSetId")
    public void setDuplicateRecordSetId(String DuplicateRecordSetId) {
        this.DuplicateRecordSetId = DuplicateRecordSetId;
    }

    private String RecordId;

    @JsonProperty("RecordId")
    public String getRecordId() {
        return this.RecordId;
    }

    @JsonProperty("RecordId")
    public void setRecordId(String RecordId) {
        this.RecordId = RecordId;
    }

     @XStreamAlias("Record")
    private AbstractDescribedSObjectBase Record;

    @JsonProperty("Record")
    public AbstractDescribedSObjectBase getRecord() {
        return this.Record;
    }

    @JsonProperty("Record")
    public void setRecord(AbstractDescribedSObjectBase Record) {
        this.Record = Record;
    }
 
    @Override
    public final SObjectDescription description() {
        return DESCRIPTION;
    }

    private static SObjectDescription createSObjectDescription() {
        final SObjectDescription description = new SObjectDescription();



        final List<SObjectField> fields1 = new ArrayList<>();
        description.setFields(fields1);

        final SObjectField sObjectField1 = createField("Id", "Duplicate Record Item ID", "id", "tns:ID", 18, false, false, false, false, false, false, true);
        fields1.add(sObjectField1);
        final SObjectField sObjectField2 = createField("IsDeleted", "Deleted", "boolean", "xsd:boolean", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField2);
        final SObjectField sObjectField3 = createField("Name", "Duplicate Record Item Name", "string", "xsd:string", 255, false, false, true, false, false, false, true);
        fields1.add(sObjectField3);
        final SObjectField sObjectField4 = createField("CreatedDate", "Created Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField4);
        final SObjectField sObjectField5 = createField("CreatedById", "Created By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField5);
        final SObjectField sObjectField6 = createField("LastModifiedDate", "Last Modified Date", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField6);
        final SObjectField sObjectField7 = createField("LastModifiedById", "Last Modified By ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField7);
        final SObjectField sObjectField8 = createField("SystemModstamp", "System Modstamp", "datetime", "xsd:dateTime", 0, false, false, false, false, false, false, false);
        fields1.add(sObjectField8);
        final SObjectField sObjectField9 = createField("DuplicateRecordSetId", "Duplicate Record Set ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField9);
        final SObjectField sObjectField10 = createField("RecordId", "Record ID", "reference", "tns:ID", 18, false, false, false, false, false, false, false);
        fields1.add(sObjectField10);

        description.setLabel("Duplicate Record Item");
        description.setLabelPlural("Duplicate Record Items");
        description.setName("DuplicateRecordItem");

        final SObjectDescriptionUrls sObjectDescriptionUrls1 = new SObjectDescriptionUrls();
        sObjectDescriptionUrls1.setApprovalLayouts("/services/data/v34.0/sobjects/DuplicateRecordItem/describe/approvalLayouts");
        sObjectDescriptionUrls1.setCompactLayouts("/services/data/v34.0/sobjects/DuplicateRecordItem/describe/compactLayouts");
        sObjectDescriptionUrls1.setDescribe("/services/data/v34.0/sobjects/DuplicateRecordItem/describe");
        sObjectDescriptionUrls1.setLayouts("/services/data/v34.0/sobjects/DuplicateRecordItem/describe/layouts");
        sObjectDescriptionUrls1.setRowTemplate("/services/data/v34.0/sobjects/DuplicateRecordItem/{ID}");
        sObjectDescriptionUrls1.setSobject("/services/data/v34.0/sobjects/DuplicateRecordItem");
        sObjectDescriptionUrls1.setUiDetailTemplate("https://gabrielchanca-dev-ed.my.salesforce.com/{ID}");
        sObjectDescriptionUrls1.setUiEditTemplate("https://gabrielchanca-dev-ed.my.salesforce.com/{ID}/e");
        sObjectDescriptionUrls1.setUiNewRecord("https://gabrielchanca-dev-ed.my.salesforce.com/0GL/e");
        description.setUrls(sObjectDescriptionUrls1);

        return description;
    }
}
