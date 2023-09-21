package com.fundguide.melona.member.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
        throw new RuntimeException("정의되지 않은 멤버 제한 상태입니다.");
    }
}
