package com.fundguide.melona.board.leaderboard.dto;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardDto {

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
    private int boardCount;

    private String fileName;
    private String filePath;


    public  static ModelMapper modelMapper = new ModelMapper();
    public static LeaderBoardDto toLeaderBoardDto(LeaderBoardEntity leaderBoardEntity){
        return modelMapper.map(leaderBoardEntity, LeaderBoardDto.class);
    }
}
