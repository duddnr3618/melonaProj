package com.fundguide.melona.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column @Email
  private String memberEmail;
  private String memberName;

  private String memberPassword;
  private String memberRole;
  private String memberAddress;
  private LocalDate memberJoinData;
  private String memberAvailable;
  private String memberNickname;

}
