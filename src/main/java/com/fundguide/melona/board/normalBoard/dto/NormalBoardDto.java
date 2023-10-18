package com.fundguide.melona.board.normalBoard.dto;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

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
    private int boardCount;

    private String fileName;
    private String filePath;


    public  static ModelMapper modelMapper = new ModelMapper();
    public static NormalBoardDto toNormalBoardDto(NormalBoardEntity normalBoardEntity){
        return modelMapper.map(normalBoardEntity, NormalBoardDto.class);
    }
}
