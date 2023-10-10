package com.fundguide.melona.board.community.service;

import com.fundguide.melona.board.community.dto.CommentDto;
import com.fundguide.melona.board.community.entity.CommentEntity;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommentRepository;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;

    public Long save(CommentDto commentDto) {
        /* 부모 엔티티 조회 */
        Optional<CommunityEntity> optionalCommunityEntity = communityRepository.findById(commentDto.getBoardId());
        System.out.println(">>>>>>>>> optionalCommunityEntity: " + optionalCommunityEntity);
        if (optionalCommunityEntity.isPresent()) {
            CommunityEntity communityEntity = optionalCommunityEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, communityEntity);
            System.out.println(">>>>>>>> commentEntity :  " + commentEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }

    }

    public List<CommentDto> findAll(Long boardId) {
        CommunityEntity communityEntity = communityRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByCommunityEntityOrderByIdDesc(communityEntity);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList) {
           CommentDto commentDto = CommentDto.toCommentDto(commentEntity);
           commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
