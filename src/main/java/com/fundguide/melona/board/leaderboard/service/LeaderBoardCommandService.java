package com.fundguide.melona.board.leaderboard.service;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class LeaderBoardCommandService {
    private final LeaderBoardRepository leaderBoardRepository;
    public void save(LeaderBoardDto leaderBoardDto) {
        LeaderBoardEntity leaderBoardEntity = LeaderBoardEntity.toSaveEntity(leaderBoardDto);
        leaderBoardRepository.save(leaderBoardEntity);
    }
}
