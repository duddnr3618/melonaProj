package com.fundguide.melona.member.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardImpeachEntity;
import com.fundguide.melona.member.role.MemberLimitConvert;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleConvert;
import com.fundguide.melona.member.role.MemberRoleState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  private String memberEmail;
  private String memberName;
  private String memberPassword;

  @Convert(converter = MemberRoleConvert.class)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "USER")
  private MemberRoleState memberRole;

  private String memberAddress;
  private LocalDate memberJoinDate;
  private String memberNickname;

  @PrePersist
  protected void onCreate() {
    memberJoinDate = LocalDate.now();
  }

  /**디폴트값 NORMAL이니 특수한 경우를 제외한 경우에는 따로 지정하지 않아도 됨*/
  @Convert(converter = MemberLimitConvert.class)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @ColumnDefault("'NORMAL'")
  private MemberLimitState memberLimitState;
}
