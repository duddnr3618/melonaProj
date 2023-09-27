package com.fundguide.melona.management.service;

import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import com.fundguide.melona.management.dto.MemberRoleFilterDTO;
import com.fundguide.melona.management.service.filter.LeaderBoardCategoryHandler;
import com.fundguide.melona.management.service.filter.NormalBoardCategoryHandler;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.role.MemberLimitState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final MemberRepository memberRepository;
    private final MemberRepositoryData memberRepositoryData;
    private final NormalBoardRepository normalBoardRepository;
    private final LeaderBoardRepository leaderBoardRepository;

    /**
     * 각 카테고리 마다 필터처리된 페이징을 반환하는 서비스 메서드
     * @return FilterPage
     */
    public Page<?> getBoardCategoryFilterPaging(String category, String filter, Pageable pageable) throws IllegalAccessException {
        if (category.equals("normal")) {
            NormalBoardCategoryHandler normalBoardCategoryHandler = new NormalBoardCategoryHandler(normalBoardRepository);
            return normalBoardCategoryHandler.handleFilterCategory(filter, pageable);
        } else if (category.equals("leader")) {
            LeaderBoardCategoryHandler leaderBoardCategoryHandler = new LeaderBoardCategoryHandler(leaderBoardRepository);
            return leaderBoardCategoryHandler.handleFilterCategory(filter, pageable);
        } else {
            throw new IllegalAccessException("정의된 카테고리 값이 아닙니다.");
        }
    }

    /** 각 제한에 따른 멤버 페이지를 반환하기 위한 서비스 메서드 */
    public Page<MemberEntity> getMemberLimitStatePaging(String limit, Pageable pageable) throws NoSuchElementException {
        MemberLimitState limitState = MemberLimitState.getLimitState(limit);
        return memberRepository.memberLimitStatePage(limitState, pageable);
    }

    public Page<MemberRoleFilterDTO> getMemberRoleStatePaging(String filter, Pageable pageable) throws NoSuchElementException, IllegalAccessException {
        switch (filter) {
            case "autoGet", "setByAdmin, minSatisfy" -> {
                return memberRepository.memberRoleStatePage(filter, pageable);
            }
            case "all" -> {
                return memberRepository.memberRoleStatePage(pageable);
            }
            default -> throw new IllegalAccessException("정의되지 않은 필터 값 입니다.");
        }
    }
}
