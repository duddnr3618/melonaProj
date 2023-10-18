package com.fundguide.melona.board.normalBoard.service;

import com.fundguide.melona.board.normalBoard.dto.CommentNormalBoardDto;
import com.fundguide.melona.board.normalBoard.entity.CommentNormalBoardEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.CommentNormalBoardRepository;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentNormalBoardService {

    private final CommentNormalBoardRepository commentNormalBoardRepository;
    private final NormalBoardRepository normalBoardRepository;

    public Long save(CommentNormalBoardDto commentNormalBoardDto) {
        /* 부모 엔티티 조회 */
        Optional<NormalBoardEntity> optionalNormalBoardEntity = normalBoardRepository.findById(commentNormalBoardDto.getBoardId());
        System.out.println(">>>>>>>>> optionalCommunityEntity: " + optionalNormalBoardEntity);
        if (optionalNormalBoardEntity.isPresent()) {
            NormalBoardEntity normalBoardEntity = optionalNormalBoardEntity.get();
            CommentNormalBoardEntity commentNormalBoardEntity = CommentNormalBoardEntity.toSaveNormalBoardEntity(commentNormalBoardDto, normalBoardEntity);
            System.out.println(">>>>>>>> commentLeaderBoardEntity :  " + commentNormalBoardEntity);
            return commentNormalBoardRepository.save(commentNormalBoardEntity).getId();
        } else {
            return null;
        }

    }

    public List<CommentNormalBoardDto> findAll(Long boardId) {
        NormalBoardEntity normalBoardEntity = normalBoardRepository.findById(boardId).get();
        List<CommentNormalBoardEntity> commentNormalBoardEntityList = commentNormalBoardRepository.findAllByNormalBoardEntityOrderByIdDesc(normalBoardEntity);
        List<CommentNormalBoardDto> commentNormalBoardDtoList = new ArrayList<>();
        for(CommentNormalBoardEntity commentNormalBoardEntity : commentNormalBoardEntityList) {
            CommentNormalBoardDto commentNormalBoardDto = CommentNormalBoardDto.toCommentNormalBoardDto(commentNormalBoardEntity);
            commentNormalBoardDtoList.add(commentNormalBoardDto);
        }
        return commentNormalBoardDtoList;
    }


}
