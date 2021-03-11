package com.funkyjester.demo.integration.convert;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * use this to just return OffsetDateTime formats as ISO8601 strings
 * Otherwise, generates an ugly json dictionary splitting out time components by default
 */
public class SimpleLocalDateSerializer extends StdConverter<LocalDate, String> {
    @Override
    public String convert(LocalDate localDate) {
        return localDate.toString();
    }
}
