package com.fundguide.melona.management.dto;

import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class QMemberRoleFilterDTO extends EntityPathBase<MemberRoleFilterDTO> {

    public StringPath memberName;
    public StringPath memberNickname;
    public EnumPath<MemberRoleState> memberRole;
    public EnumPath<MemberLimitState> memberLimitState;

    public QMemberRoleFilterDTO(StringPath memberName, StringPath memberNickname, EnumPath<MemberRoleState> memberRole, EnumPath<MemberLimitState> memberLimitState) {
        super(MemberRoleFilterDTO.class, memberName.getMetadata());
        this.memberName = memberName;
        this.memberNickname = memberNickname;
        this.memberRole = memberRole;
        this.memberLimitState = memberLimitState;
    }
}
