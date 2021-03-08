package com.funkyjester.demo.integration.model.zoho;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Accounts extends PagedResponse {
    @JsonProperty
    List<Account> data;
}
