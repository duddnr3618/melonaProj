package com.fundguide.melona.member.entity;

import com.fundguide.melona.member.role.MemberLimitConvert;
import com.fundguide.melona.member.role.MemberLimitState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

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

  private String memberEmail;
  private String memberName;
  private String memberPassword;
  private String memberRole;
  private String memberAddress;
  private LocalDate memberJoinData;
  private String memberAvailable;
  private String memberNickname;

  @Convert(converter = MemberLimitConvert.class)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "NORMAL")
  private MemberLimitState memberLimitState;
}
