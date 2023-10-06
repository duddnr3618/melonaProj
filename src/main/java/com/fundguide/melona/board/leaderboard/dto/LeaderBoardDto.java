package com.fundguide.melona.board.leaderboard.dto;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardDto {

    private Long id;
    @NotBlank(message = "작성자를 입력해주세요.")
    private String boardWriter;
    @NotBlank(message = "제목을 입력해주세요.")
    private String boardTitle;
    @NotBlank(message = "내용을 입력해주세요.")
    private String boardContents;
    private long boardHits; //조회수
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public LeaderBoardDto (Long id, String boardWriter, String boardTitle, long boardHits, LocalDateTime createdTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.createdTime = createdTime;
    }

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
