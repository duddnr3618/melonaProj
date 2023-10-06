package com.fundguide.melona.member.dto;

import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.fundguide.melona.member.entity.QMemberEntity.memberEntity;

@Setter @Getter
public class MemberLeastDTO {

    private Long id;
    private String memberEmail;
    private String memberName;
    private String memberNickname;
    private String memberAvailable;
    private MemberRoleState memberRole;
    private MemberLimitState memberLimitState;
    private LocalDate memberJoinDate;

    public static QBean<MemberLeastDTO> projections () {
        return Projections.bean(
                MemberLeastDTO.class
                , memberEntity.id
                , memberEntity.memberEmail
                , memberEntity.memberName
                , memberEntity.memberNickname
                , memberEntity.memberAvailable
                , memberEntity.memberRole
                , memberEntity.memberLimitState
                , memberEntity.memberJoinDate
        );
    }
}
