package com.funkyjester.demo.integration.model.zoho;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Approval {
    @JsonProperty
    boolean delegate;
    @JsonProperty
    boolean approve;
    @JsonProperty
    boolean reject;
    @JsonProperty
    boolean resubmit;
}
