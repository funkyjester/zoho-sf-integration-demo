package com.funkyjester.demo.integration.convert;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * deserializer for date formats
 */
public class SimpleLocalDateDeSerializer extends StdConverter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
