package com.fundguide.melona.management.dto;

import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;

public record MemberRoleFilterDTO (
        String memberName
        , String memberNickname
        , MemberRoleState memberRole
        , MemberLimitState memberLimitState
) {
}
