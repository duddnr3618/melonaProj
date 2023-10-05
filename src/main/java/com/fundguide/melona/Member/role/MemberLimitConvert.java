package com.fundguide.melona.Member.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.NoSuchElementException;

@Converter
public class MemberLimitConvert implements AttributeConverter<com.fundguide.melona.member.role.MemberLimitState, String> {

    @Override
    public String convertToDatabaseColumn(com.fundguide.melona.member.role.MemberLimitState attribute) {
        return attribute.toString();
    }

    @Override
    public com.fundguide.melona.member.role.MemberLimitState convertToEntityAttribute(String dbData) {
        for (com.fundguide.melona.member.role.MemberLimitState memberLimitState : com.fundguide.melona.member.role.MemberLimitState.values()) {
            if (memberLimitState.toString().equals(dbData)) {
                return memberLimitState;
            }
        }
        throw new NoSuchElementException("유저 상태값 존재하지 않음");
    }
}
