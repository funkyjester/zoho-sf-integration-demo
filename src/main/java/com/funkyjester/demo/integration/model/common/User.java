package com.funkyjester.demo.integration.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends Record {
    @JsonProperty
    String alias;
    @JsonProperty
    Boolean confirm;
    @JsonProperty
    String email;
    @JsonProperty
    String firstName;
    @JsonProperty
    String fullName;
    @JsonProperty
    String lastName;
}
