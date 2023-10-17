package com.fundguide.melona.board.leaderboard.service;

import com.fundguide.melona.board.community.dto.CommentDto;
import com.fundguide.melona.board.community.entity.CommentEntity;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommentRepository;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import com.fundguide.melona.board.leaderboard.dto.CommentLeaderBoardDto;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.CommentLeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.CommentLeaderBoardRepository;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLeaderBoardService {

    private final CommentLeaderBoardRepository commentLeaderBoardRepository;
    private final LeaderBoardRepository leaderBoardRepository;

    public Long save(CommentLeaderBoardDto commentLeaderBoardDto) {
        System.out.println("서비스 진입");
        /* 부모 엔티티 조회 */
        Optional<LeaderBoardEntity> optionalLeaderBoardEntity = leaderBoardRepository.findById(commentLeaderBoardDto.getBoardId());
        System.out.println(">>>>>>>>> optionalCommunityEntity: " + optionalLeaderBoardEntity);
        if (optionalLeaderBoardEntity.isPresent()) {
            System.out.println("값 존재함");
            LeaderBoardEntity leaderBoardEntity = optionalLeaderBoardEntity.get();
            CommentLeaderBoardEntity commentLeaderBoardEntity = CommentLeaderBoardEntity.toSaveLeaderBoardEntity(commentLeaderBoardDto, leaderBoardEntity);
            System.out.println(">>>>>>>> commentLeaderBoardEntity :  " + commentLeaderBoardEntity);
            return commentLeaderBoardRepository.save(commentLeaderBoardEntity).getId();
        } else {
            System.out.println("값 없음");
            throw new IllegalAccessError("해당 게시판 찾지 못함");
        }

    }

    public List<CommentLeaderBoardDto> findAll(Long boardId) {
        LeaderBoardEntity leaderBoardEntity = leaderBoardRepository.findById(boardId).get();
        List<CommentLeaderBoardEntity> commentLeaderBoardEntityList = commentLeaderBoardRepository.findAllByLeaderBoardEntityOrderByIdDesc(leaderBoardEntity);
        List<CommentLeaderBoardDto> commentLeaderBoardDtoList = new ArrayList<>();
        for(CommentLeaderBoardEntity commentLeaderBoardEntity : commentLeaderBoardEntityList) {
            CommentLeaderBoardDto commentLeaderBoardDto = CommentLeaderBoardDto.toCommentLeaderBoardDto(commentLeaderBoardEntity);
            commentLeaderBoardDtoList.add(commentLeaderBoardDto);
        }
        return commentLeaderBoardDtoList;
    }


}
