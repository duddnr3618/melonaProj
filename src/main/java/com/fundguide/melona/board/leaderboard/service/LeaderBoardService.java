package com.fundguide.melona.board.leaderboard.service;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class LeaderBoardService {
    private final LeaderBoardRepository leaderBoardRepository;
    public void save(LeaderBoardDto leaderBoardDto) {
        LeaderBoardEntity leaderBoardEntity = LeaderBoardEntity.toSaveEntity(leaderBoardDto);
        leaderBoardRepository.save(leaderBoardEntity);
    }

    public List<LeaderBoardDto> findAll() {
        List<LeaderBoardEntity> leaderBoardEntityList = leaderBoardRepository.findAll();
        List<LeaderBoardDto> leaderBoardDtoList = new ArrayList<>();
        for(LeaderBoardEntity leaderBoardEntity : leaderBoardEntityList) {
            leaderBoardDtoList.add(LeaderBoardDto.toLeaderBoardDto(leaderBoardEntity));
        }
        return leaderBoardDtoList;

    }

    @Transactional
    // 조회수 증가 메소드
    public void updateHits(Long id) {
        leaderBoardRepository.updateHits(id);
    }

    public LeaderBoardDto findById(Long id) {
        Optional<LeaderBoardEntity> optionalLeaderBoard = leaderBoardRepository.findById(id);

        if(optionalLeaderBoard.isPresent()){
            LeaderBoardEntity leaderBoardEntity = optionalLeaderBoard.get();
            LeaderBoardDto leaderBoardDto = LeaderBoardDto.toLeaderBoardDto(leaderBoardEntity);
            return leaderBoardDto;
        }else {
            return null;
        }
    }

    public LeaderBoardDto update(LeaderBoardDto leaderBoardDto) {
        LeaderBoardEntity leaderBoardEntity = LeaderBoardEntity.toUpdateEntity(leaderBoardDto);
        leaderBoardRepository.save(leaderBoardEntity);
        return findById(leaderBoardDto.getId());

    }
}
