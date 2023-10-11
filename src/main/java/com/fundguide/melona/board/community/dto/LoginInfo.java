package com.fundguide.melona.board.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
public class LoginInfo {
    private Long memberId;
    private String memberEmail;
    private String memberName;
}
