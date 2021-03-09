package com.funkyjester.demo.integration.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.funkyjester.demo.integration.convert.SimpleOffsetDateTimeDeSerializer;
import com.funkyjester.demo.integration.convert.SimpleOffsetDateTimeSerializer;
import com.funkyjester.demo.integration.model.Action;
import com.funkyjester.demo.integration.model.SOR;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Record {
    @JsonProperty
    String salesforceId;

    @JsonProperty("zohoId")
    String zohoId;

    @JsonProperty
    SOR sor;
    @JsonProperty
    Action action;

    @JsonProperty
    User createdBy;
    @JsonProperty
    @JsonSerialize(converter = SimpleOffsetDateTimeSerializer.class)
    @JsonDeserialize(converter = SimpleOffsetDateTimeDeSerializer.class)
    OffsetDateTime createTime;

    @JsonProperty
    User modifiedBy;
    @JsonProperty
    @JsonSerialize(converter = SimpleOffsetDateTimeSerializer.class)
    @JsonDeserialize(converter = SimpleOffsetDateTimeDeSerializer.class)
    OffsetDateTime modifiedTime;

}
