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
  private String memberAvailable;
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

  /**JSON으로 변환시 서로 순환 참조가 되어 반복적으로 출력되어 JSON에서 이를 무한 루프라고 판단하여
   * 에러가 발생함 그렇기에 @JsonBackReference 추가.
   * 각 impeach를 테이블을 나누어 작성하다보니 발생 경우라고 판단함.
   * JsonBackReference = 관계에서 역방향(부모->자식) 참조로 어노테이션을 추가하면 직렬화에서 제외된다*/
  @JsonBackReference
  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  private Set<NormalBoardImpeachEntity> boardImpeachEntity = new HashSet<>();
}
