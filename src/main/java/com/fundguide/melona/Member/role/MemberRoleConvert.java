package com.fundguide.melona.Member.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.NoSuchElementException;

@Converter
public class MemberRoleConvert implements AttributeConverter<com.fundguide.melona.member.role.MemberRoleState, String> {

    @Override
    public String convertToDatabaseColumn(com.fundguide.melona.member.role.MemberRoleState attribute) {
        return attribute.toString();
    }

    @Override
    public com.fundguide.melona.member.role.MemberRoleState convertToEntityAttribute(String dbData) {
        for (com.fundguide.melona.member.role.MemberRoleState memberRoleState : com.fundguide.melona.member.role.MemberRoleState.values()) {
            if (memberRoleState.toString().equals(dbData)) {
                return memberRoleState;
            }
        }
        throw new NoSuchElementException("유저 상태값 존재하지 않음");
    }
}
