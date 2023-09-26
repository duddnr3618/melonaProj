package com.fundguide.melona.management.dto;

import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QMemberRoleFilterDTO extends EntityPathBase<MemberRoleFilterDTO> {

    public QMemberRoleFilterDTO(Class<? extends MemberRoleFilterDTO> type, String variable) {
        super(type, variable);
    }

    public QMemberRoleFilterDTO(Class<? extends MemberRoleFilterDTO> type, PathMetadata metadata) {
        super(type, metadata);
    }

    public QMemberRoleFilterDTO(Class<? extends MemberRoleFilterDTO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
    }


    public QMemberRoleFilterDTO(PathMetadata metadata, PathInits inits, StringPath memberName, StringPath memberNickname, EnumPath<MemberRoleState> memberRole, EnumPath<MemberLimitState> memberLimitState) {
        super(MemberRoleFilterDTO.class, metadata, inits);
    }

    public StringPath memberName = createString("memberName");
    public StringPath memberNickname = createString("memberNickname");
    public EnumPath<MemberRoleState> memberRole = createEnum("memberRole", MemberRoleState.class);
    public EnumPath<MemberLimitState> memberLimitState = createEnum("memberLimitState", MemberLimitState.class);

    public QMemberRoleFilterDTO(StringPath memberName, StringPath memberNickname, EnumPath<MemberRoleState> memberRole, EnumPath<MemberLimitState> memberLimitState) {
        super(MemberRoleFilterDTO.class, memberName.getMetadata());
        this.memberName = memberName;
        this.memberNickname = memberNickname;
        this.memberRole = memberRole;
        this.memberLimitState = memberLimitState;
    }
}
