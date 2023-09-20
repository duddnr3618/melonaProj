package com.fundguide.melona.member.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDto {
    private Long id;

    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private String memberRole;
    private String memberAddress;
    private LocalDate memberJoinData;
    private String memberAvailable;
    private String memberNickname;
}
