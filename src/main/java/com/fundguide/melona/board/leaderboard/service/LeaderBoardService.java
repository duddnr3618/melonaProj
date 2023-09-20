package com.fundguide.melona.board.leaderboard.service;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public void delete(Long id) {
        leaderBoardRepository.deleteById(id);

    }


    public Page<LeaderBoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageLimit = 3;
        Page<LeaderBoardEntity> leaderBoardEntities =
        leaderBoardRepository.findAll(PageRequest.of(page , pageLimit , Sort.by(Sort.Direction.DESC,"id")));

        System.out.println("boardEntities.getContent() = " + leaderBoardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + leaderBoardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + leaderBoardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + leaderBoardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + leaderBoardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + leaderBoardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + leaderBoardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + leaderBoardEntities.isLast()); // 마지막 페이지 여부

        Page<LeaderBoardDto> leaderBoardDtos = leaderBoardEntities.map(leaderBoardEntity -> new LeaderBoardDto(
                leaderBoardEntity.getId(), leaderBoardEntity.getBoardWriter(), leaderBoardEntity.getBoardTitle(), leaderBoardEntity.getBoardHits(), leaderBoardEntity.getCreatedTime()
        ));
        return leaderBoardDtos;
    }
}
