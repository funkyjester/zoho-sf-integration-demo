package com.funkyjester.demo.integration.model.zoho;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.funkyjester.demo.integration.model.Record;

public class User extends Record {
    @JsonProperty("name")
    String name;
    @JsonProperty("email")
    String email;
}
