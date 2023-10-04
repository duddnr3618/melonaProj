package com.fundguide.melona.management.service.filter;

import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class LeaderBoardCategoryHandler implements FilterCategoryHandler {
    private final LeaderBoardRepository leaderBoardRepository;

    @Override
    public Page<?> waringPage(Pageable pageable) {
        return null;
    }

    @Override
    public Page<?> blockPage(Pageable pageable) {
        return null;
    }

    @Override
    public Page<?> allPage(Pageable pageable) {
        return leaderBoardRepository.findAll(pageable);
    }
}
