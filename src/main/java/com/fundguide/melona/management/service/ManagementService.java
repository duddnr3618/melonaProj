package com.fundguide.melona.management.service;

import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import com.fundguide.melona.management.service.filter.CommunityBoardCategoryHandler;
import com.fundguide.melona.member.dto.MemberLeastDTO;
import com.fundguide.melona.management.service.filter.LeaderBoardCategoryHandler;
import com.fundguide.melona.management.service.filter.NormalBoardCategoryHandler;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.role.MemberLimitState;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.lock.PessimisticReadUpdateLockingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final MemberRepository memberRepository;
    private final MemberRepositoryData memberRepositoryData;
    private final NormalBoardRepository normalBoardRepository;
    private final LeaderBoardRepository leaderBoardRepository;
    private final CommunityRepository communityRepository;

    /**
     * 각 카테고리 마다 필터처리된 페이징을 반환하는 서비스 메서드
     *
     * @return FilterPage
     */
    public Page<?> getBoardCategoryFilterPaging(String category, String filter, Pageable pageable) throws IllegalAccessException {
        switch (category) {
            case "normal" -> {
                NormalBoardCategoryHandler normalBoardCategoryHandler = new NormalBoardCategoryHandler(normalBoardRepository);
                return normalBoardCategoryHandler.handleFilterCategory(filter, pageable);
            }
            case "leader" -> {
                LeaderBoardCategoryHandler leaderBoardCategoryHandler = new LeaderBoardCategoryHandler(leaderBoardRepository);
                return leaderBoardCategoryHandler.handleFilterCategory(filter, pageable);
            }
            case "community" -> {
                CommunityBoardCategoryHandler communityBoardCategoryHandler = new CommunityBoardCategoryHandler(communityRepository);
                return communityBoardCategoryHandler.handleFilterCategory(filter, pageable);
            }
            default -> throw new IllegalAccessException("정의된 카테고리 값이 아닙니다.");
        }
    }

    /** 카테고리와 id값으로 해당 게시물을 비활성화 하는 메서드
     * 비동기식으로 처리를 위해 status값을 반환함.*/
    public ResponseEntity<String> modifyDisableBoard(String category, Long boardId) throws IllegalAccessException {
        switch (category) {
            case "normal" -> {
                Optional<NormalBoardEntity> optional = normalBoardRepository.findById(boardId);
                optional.ifPresentOrElse(o -> {
                    o.setBoardUsing(BoardUsing.BLOCK);
                    normalBoardRepository.save(o);
                }, () -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                return ResponseEntity.ok().build();
            }
            case "leader" -> {
                Optional<LeaderBoardEntity> optional = leaderBoardRepository.findById(boardId);
                /*optional.ifPresent(o -> );*/
                return null;
            }
            default -> throw new IllegalAccessException("정의된 카테고리 값이 아닙니다.");
        }
    }

    public Page<MemberLeastDTO> getMemberEvaluatePendingByRule() {
        return null;
    }

    public Page<MemberLeastDTO> getMemberRoleStatePaging(String filter, Pageable pageable) {
        if (!"all".equals(filter)) {
            return memberRepository.memberRoleStateFilterPage(filter, pageable);
        } else {
            return memberRepository.findAllOfMemberLeastData(pageable);
        }
    }
}
