package com.fundguide.melona.management.service.filter;

import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class LeaderBoardCategoryHandler implements FilterCategoryHandler {
    private final LeaderBoardRepository leaderBoardRepository;

    @Override
    public Page<?> handleFilterCategory(String filter, Pageable pageable) {
        return FilterCategoryHandler.super.handleFilterCategory(filter, pageable);
    }

    @Override
    public Page<?> waringPage(Pageable pageable) {
        return leaderBoardRepository.onlyViewFilterByWaring(pageable);
    }

    @Override
    public Page<?> blockPage(Pageable pageable) {
        return leaderBoardRepository.onlyViewFilterByBlock(pageable);
    }

    @Override
    public Page<?> allPage(Pageable pageable) {
        return leaderBoardRepository.findAll(pageable);
    }
}
