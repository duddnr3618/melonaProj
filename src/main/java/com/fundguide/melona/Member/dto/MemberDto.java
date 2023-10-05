package com.fundguide.melona.Member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
@Validated
@Data
public class MemberDto {
    private Long id;
    @NotEmpty
    private String memberEmail;
    private String memberName;
    @NotEmpty
    private String memberPassword;
    private String memberRole;
    private String memberAddress;
    private LocalDate memberJoinDate;
    private String memberAvailable;
    @NotEmpty
    private String memberNickname;
}
