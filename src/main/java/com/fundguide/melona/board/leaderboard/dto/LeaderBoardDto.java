package com.fundguide.melona.board.leaderboard.dto;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardDto {

    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private long boardHits; //조회수
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;


    public static LeaderBoardDto toLeaderBoardDto (LeaderBoardEntity leaderBoardEntity) {
        LeaderBoardDto leaderBoardDto = new LeaderBoardDto();
        leaderBoardDto.setId(leaderBoardEntity.getId());
        leaderBoardDto.setBoardWriter(leaderBoardEntity.getBoardWriter());
        leaderBoardDto.setBoardTitle(leaderBoardEntity.getBoardTitle());
        leaderBoardDto.setBoardContents(leaderBoardEntity.getBoardContents());
        leaderBoardDto.setBoardHits(leaderBoardEntity.getBoardHits());
        leaderBoardDto.setCreatedTime(leaderBoardEntity.getCreatedTime());
        leaderBoardDto.setUpdatedTime(leaderBoardEntity.getUpdatedTime());
        return leaderBoardDto;
    }
}
