package com.richfood.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Converter
public class StringToOffsetTimeConverter implements AttributeConverter<String, OffsetTime> {

    @Override
    public OffsetTime convertToDatabaseColumn(String attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        return OffsetTime.parse(attribute, DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneOffset.UTC));
    }

    @Override
    public String convertToEntityAttribute(OffsetTime dbData) {
        if (dbData == null) {
            return null;
        }

        return dbData.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
