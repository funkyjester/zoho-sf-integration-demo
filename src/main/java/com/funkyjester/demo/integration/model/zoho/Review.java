package com.funkyjester.demo.integration.model.zoho;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty
    boolean approve;
    @JsonProperty
    boolean reject;
    @JsonProperty
    boolean resubmit;
}
