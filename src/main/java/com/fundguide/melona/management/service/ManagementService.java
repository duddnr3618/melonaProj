package com.fundguide.melona.management.service;

import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import com.fundguide.melona.management.service.filter.LeaderBoardCategoryHandler;
import com.fundguide.melona.management.service.filter.NormalBoardCategoryHandler;
import com.fundguide.melona.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final MemberRepository memberRepository;
    private final NormalBoardRepository normalBoardRepository;
    private final LeaderBoardRepository leaderBoardRepository;

    public Page<?> getBoardCategoryFilterPaging(String category, String filter, Pageable pageable) throws IllegalAccessException {
        if (category.equals("normal")) {
            NormalBoardCategoryHandler normalBoardCategoryHandler = new NormalBoardCategoryHandler();
            return normalBoardCategoryHandler.handleFilterCategory(filter, pageable);
        } else if (category.equals("leader")) {
            LeaderBoardCategoryHandler leaderBoardCategoryHandler = new LeaderBoardCategoryHandler();
            return leaderBoardCategoryHandler.handleFilterCategory(filter, pageable);
        } else {
            throw new IllegalAccessException("정의된 카테고리 값이 아닙니다.");
        }
    }
}
