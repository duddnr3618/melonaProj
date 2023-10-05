package com.fundguide.melona.member.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.NoSuchElementException;

@Converter
public class MemberLimitConvert implements AttributeConverter<MemberLimitState, String> {

    @Override
    public String convertToDatabaseColumn(MemberLimitState attribute) {
        return attribute.toString();
    }

    @Override
    public MemberLimitState convertToEntityAttribute(String dbData) {
        for (MemberLimitState memberLimitState : MemberLimitState.values()) {
            if (memberLimitState.toString().equals(dbData)) {
                return memberLimitState;
            }
        }
        throw new NoSuchElementException("유저 상태값 존재하지 않음");
    }
}
