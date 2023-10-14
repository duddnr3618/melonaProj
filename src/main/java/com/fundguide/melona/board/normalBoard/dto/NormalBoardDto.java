package com.fundguide.melona.board.normalBoard.dto;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "제목을 입력해주세요")
    private String boardTitle;
    @NotNull(message = "내용을 입력해주세요.")
    private String boardContents;
    private int boardHits;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Long memberId;
    private String memberName;

    private String fileName;
    private String filePath;

    public static NormalBoardDto toBoardDto(NormalBoardEntity normalBoardEntity) {
        return NormalBoardDto.builder()
                .id(normalBoardEntity.getId())
                .boardTitle(normalBoardEntity.getBoardTitle())
                .boardContents(normalBoardEntity.getBoardContents())
                .boardHits(normalBoardEntity.getBoardHits())
                .createdTime(normalBoardEntity.getCreatedTime())
                .updatedTime(normalBoardEntity.getUpdatedTime())
                .filePath(normalBoardEntity.getFilePath())
                .build();
    }
}
