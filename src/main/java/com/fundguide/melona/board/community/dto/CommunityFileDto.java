package com.fundguide.melona.board.community.dto;

import com.fundguide.melona.board.community.entity.CommunityFileEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityFileDto {

    private String fileName;
    private String originalFileName;
    private String fileUrl;

    /* entity -> dto 변환 */
    private static ModelMapper modelMapper = new ModelMapper();
    public static CommunityFileDto of(CommunityFileEntity communityFileEntity){
        return modelMapper.map(communityFileEntity, CommunityFileDto.class);
    }
}
