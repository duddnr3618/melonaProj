package com.fundguide.melona.member.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.NoSuchElementException;

@Converter
public class MemberRoleConvert implements AttributeConverter<MemberRoleState, String> {

    @Override
    public String convertToDatabaseColumn(MemberRoleState attribute) {
        return attribute.toString();
    }

    @Override
    public MemberRoleState convertToEntityAttribute(String dbData) {
        for (MemberRoleState memberRoleState : MemberRoleState.values()) {
            if (memberRoleState.toString().equals(dbData)) {
                return memberRoleState;
            }
        }
        throw new NoSuchElementException("유저 상태값 존재하지 않음");
    }
}
