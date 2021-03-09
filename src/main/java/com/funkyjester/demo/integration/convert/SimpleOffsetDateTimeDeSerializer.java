package com.funkyjester.demo.integration.convert;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleOffsetDateTimeDeSerializer extends StdConverter<String, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(String s) {
        return OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
