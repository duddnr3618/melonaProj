package com.fundguide.melona.management.dto;

import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Getter;
import lombok.Setter;

import static com.fundguide.melona.member.entity.QMemberEntity.memberEntity;

@Setter @Getter
public class MemberRoleFilterDTO {

    private Long id;
    private String memberName;
    private String memberNickname;
    private MemberRoleState memberRole;
    private MemberLimitState memberLimitState;

    public static class QMemberRoleFilterDTO extends EntityPathBase<MemberRoleFilterDTO> {
        public NumberPath<Long> id;
        public StringPath memberName;
        public StringPath memberNickname;
        public EnumPath<MemberRoleState> memberRole;
        public EnumPath<MemberLimitState> memberLimitState;

        public QMemberRoleFilterDTO(NumberPath<Long> id, StringPath memberName, StringPath memberNickname, EnumPath<MemberRoleState> memberRole, EnumPath<MemberLimitState> memberLimitState) {
            super(MemberRoleFilterDTO.class, id.getMetadata());
            this.id = id;
            this.memberName = memberName;
            this.memberNickname = memberNickname;
            this.memberRole = memberRole;
            this.memberLimitState = memberLimitState;
        }
    }

    @SuppressWarnings("unused")
    public static QBean<MemberRoleFilterDTO> projections () {
        return Projections.bean(
                MemberRoleFilterDTO.class
                , memberEntity.id
                , memberEntity.memberName
                , memberEntity.memberNickname
                , memberEntity.memberRole
                , memberEntity.memberLimitState
        );
    }
}
