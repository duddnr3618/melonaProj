package com.fundguide.melona.board.normalBoard.entity;

import com.fundguide.melona.board.common.entity.BaseBoardEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "normal_board")
public class NormalBoardEntity extends BaseBoardEntity {

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
}
