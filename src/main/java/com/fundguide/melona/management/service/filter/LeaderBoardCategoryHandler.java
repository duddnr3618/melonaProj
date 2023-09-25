package com.fundguide.melona.management.service.filter;

import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class LeaderBoardCategoryHandler implements FilterCategoryHandler {
    private LeaderBoardRepository leaderBoardRepository;

    @Override
    public Page<?> waringPage() {
        return null;
    }

    @Override
    public Page<?> blockPage() {
        return null;
    }

    @Override
    public Page<?> allPage() {
        return null;
    }
}
