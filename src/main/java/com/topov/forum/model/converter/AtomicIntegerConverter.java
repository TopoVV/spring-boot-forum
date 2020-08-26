package com.topov.forum.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.concurrent.atomic.AtomicInteger;

@Converter
public class AtomicIntegerConverter implements AttributeConverter<AtomicInteger, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AtomicInteger atomicInteger) {
        return atomicInteger.get();
    }

    @Override
    public AtomicInteger convertToEntityAttribute(Integer integer) {
        return new AtomicInteger(integer);
    }
}
