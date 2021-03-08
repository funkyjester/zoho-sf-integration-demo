package com.funkyjester.demo.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class Record {
    @JsonProperty("id")
    String id;
}
