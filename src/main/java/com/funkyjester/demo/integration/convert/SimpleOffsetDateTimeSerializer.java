package com.funkyjester.demo.integration.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * use this to just return OffsetDateTime formats as ISO8601 strings
 * Otherwise, generates an ugly json dictionary splitting out time components by default
 */
public class SimpleOffsetDateTimeSerializer extends StdConverter<OffsetDateTime, String> {
    @Override
    public String convert(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toString();
    }
}
