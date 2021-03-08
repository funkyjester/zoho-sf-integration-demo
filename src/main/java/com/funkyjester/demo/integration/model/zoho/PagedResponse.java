package com.funkyjester.demo.integration.model.zoho;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.funkyjester.demo.integration.model.Record;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@EqualsAndHashCode @ToString
public abstract class PagedResponse {

    @JsonProperty
    Info info;

    @Getter @Setter
    @EqualsAndHashCode @ToString
    public static class Info {
        @JsonProperty
        int per_page;
        @JsonProperty
        int count;
        @JsonProperty
        int page;
        @JsonProperty
        boolean more_records;
    }
}
