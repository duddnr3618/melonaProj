package com.fundguide.melona.board.normalBoard.dto;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.management.dto.MemberRoleFilterDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fundguide.melona.board.normalBoard.entity.QNormalBoardEntity.normalBoardEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalBoardDto {

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

    public NormalBoardDto(Long id, String boardWriter, String boardTitle, long boardHits, LocalDateTime createdTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.createdTime = createdTime;
    }


    public static NormalBoardDto toLeaderBoardDto(NormalBoardEntity normalBoardEntity) {
        return NormalBoardDto.builder()
                .id(normalBoardEntity.getId())
                .boardTitle(normalBoardEntity.getBoardTitle())
                .boardWriter(normalBoardEntity.getBoardWriter())
                .boardContents(normalBoardEntity.getBoardContents())
                .boardHits(normalBoardEntity.getBoardHits())
                .createdTime(normalBoardEntity.getCreatedTime())
                .updatedTime(normalBoardEntity.getUpdatedTime())
                .build();
    }

    public static QBean<NormalBoardDto> projections () {
        return Projections.bean(
                NormalBoardDto.class
                , normalBoardEntity.id
                , normalBoardEntity.boardTitle
                , normalBoardEntity.boardWriter
                , normalBoardEntity.boardContents
                , normalBoardEntity.boardHits
                , normalBoardEntity.createdTime
                , normalBoardEntity.updatedTime
        );
    }
}
