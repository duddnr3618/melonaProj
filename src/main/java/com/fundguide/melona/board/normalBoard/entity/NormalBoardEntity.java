package com.fundguide.melona.board.normalBoard.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.like.entity.LikeEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "normal_board")
public class NormalBoardEntity extends BaseTimeEntity {


  @Id
  @Column(name = "boardId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "boardWriter")
  private String boardWriter;
  @Column(name = "boardTitle")
  private String boardTitle;
  @Column(name = "boardContents")
  private String boardContents;
  @Column(name = "boardHits")
  private long boardHits; //조회수
  @Column(name = "boardLikes")
  private long boardLikes; //좋아요

  /**JSON으로 변환시 서로 순환 참조가 되어 반복적으로 출력되어 JSON에서 이를 무한 루프라고 판단하여 에러가 발생함
   * <p>그렇기에 @JsonBackReference 추가.
   * <p> 각 impeach를 테이블을 나누어 작성하다보니 발생 경우라고 판단함.
   * <p> JsonBackReference = 관계에서 역방향(부모->자식) 참조로 어노테이션을 추가하면 직렬화에서 제외된다*/
  @JsonBackReference
  @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
  private Set<NormalBoardImpeachEntity> impeach = new HashSet<>();



  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private MemberEntity memberEntity;

  /* 좋아요 연관관계 */
  @OneToMany(mappedBy = "NormalBoardEntity", cascade = CascadeType.REMOVE)
  private List<LikeEntity> boardLike;
  @Transient
  private boolean like_state;
  @Transient
  private int like_count;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  @ColumnDefault("'0'")
  @Builder.Default
  private BoardUsing boardUsing = BoardUsing.USING;
}
