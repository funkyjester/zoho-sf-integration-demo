package com.funkyjester.demo.integration.model.zoho;


import com.funkyjester.demo.integration.model.Record;

public class PagedResponse {
    Record[] data;
    Info info;


    public class Info {
        int per_page;
        int count;
        int page;
        boolean more_records;
    }
}
