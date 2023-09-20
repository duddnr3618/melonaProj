package com.fundguide.melona.board.normalBoard.entity;

import com.fundguide.melona.board.common.entity.BaseBoardEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "normal_board")
public class NormalBoardEntity extends BaseBoardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String boardWriter;
  private String boardTitle;
  private String boardContents;
  private long boardHits; //조회수
  private long boardLikes; //좋아요



}
