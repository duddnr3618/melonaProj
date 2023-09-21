package com.fundguide.melona.board.leaderboard.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardDto {

    private Long boardId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private long boardHits; //조회수
    private long boardLikes; //좋아요
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
