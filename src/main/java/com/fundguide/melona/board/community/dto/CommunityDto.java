package com.fundguide.melona.board.community.dto;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

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
    private Long memberId;
    private String memberName;

    private String fileName;
    private String filePath;

    public static CommunityDto toBoardDto(CommunityEntity communityEntity) {
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(communityEntity.getId());
        communityDto.setBoardTitle(communityEntity.getBoardTitle());
        communityDto.setBoardContents(communityEntity.getBoardContents());
        communityDto.setBoardHits(communityEntity.getBoardHits());
        communityDto.setCreatedTime(communityEntity.getCreatedTime());
        communityDto.setUpdatedTime(communityEntity.getUpdatedTime());
        communityDto.setFilePath(communityEntity.getFilePath());
        return communityDto;
    }


}
