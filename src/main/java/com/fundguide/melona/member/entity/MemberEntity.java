package com.fundguide.melona.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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

  private String memberEmail;
  private String memberName;
  private String memberPassword;
  private String memberRole;
  private String memberAddress;
  private LocalDate memberJoinDate;
  private String memberAvailable;
  private String memberNickname;



 /* @PrePersist
  protected void onCreate() {
    memberJoinDate = LocalDate.now();
  }*/

}
