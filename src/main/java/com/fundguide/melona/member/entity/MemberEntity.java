package com.fundguide.melona.member.entity;

import com.fundguide.melona.member.role.MemberLimitConvert;
import com.fundguide.melona.member.role.MemberLimitState;
import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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



  @PrePersist
  protected void onCreate() {
    memberJoinDate = LocalDate.now();
  }

  @Convert(converter = MemberLimitConvert.class)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "NORMAL")
  private MemberLimitState memberLimitState;
}
