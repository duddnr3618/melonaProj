package com.fundguide.melona.board.community.dto;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityDto {
    private Long id;
    @NotBlank(message = "제목을 입력해주세요")
    private String boardTitle;
    @NotNull(message = "내용을 입력해주세요.")
    private String boardContents;
    private int boardHits;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String memberEmail;

    private List<CommunityFileDto> communityFileDtos = new ArrayList<>();

    private List<Long> communityFileList = new ArrayList<>();       // 파일의 번호 관리를 리스트해서 관리

    /* entity -> dto 변환 */
    private static ModelMapper modelMapper = new ModelMapper();
    public static CommunityDto of(CommunityEntity communityEntity){
      return modelMapper.map(communityEntity, CommunityDto.class);
    }


}
