package com.fundguide.melona.management.service;

import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import com.fundguide.melona.management.service.filter.CommunityBoardCategoryHandler;
import com.fundguide.melona.management.service.filter.LeaderBoardCategoryHandler;
import com.fundguide.melona.management.service.filter.NormalBoardCategoryHandler;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.role.MemberRoleState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    /**
     * 카테고리와 id값으로 해당 게시물을 비활성화 하는 메서드
     * 비동기식으로 처리를 위해 status값을 반환함.
     */
    public ResponseEntity<String> modifyDisableBoard(String category, Long boardId) throws IllegalAccessException {
        switch (category) {
            case "normal" -> {
                Optional<NormalBoardEntity> optional = normalBoardRepository.findById(boardId);
                return updateBoardUsing(optional, normalBoardRepository);
            }

            case "leader" -> {
                Optional<LeaderBoardEntity> optional = leaderBoardRepository.findById(boardId);
                return updateBoardUsing(optional, leaderBoardRepository);
            }

            case "community" -> {
                Optional<CommunityEntity> optional = communityRepository.findById(boardId);
                return updateBoardUsing(optional, communityRepository);
            }
            default -> throw new IllegalAccessException("정의된 카테고리 값이 아닙니다.");
        }
    }

    public Page<MemberEntity> getMemberEvaluatePendingByRule(String filter, Pageable pageable) {
        if (!filter.equals("all")) {
            return memberRepository.evaluatePendingByRule(filter, pageable);
        } else {
            return memberRepository.findAll(pageable);
        }
    }

    public Page<MemberEntity> getMemberAuthorityByRule(String filter, Pageable pageable) {
        if (!"all".equals(filter)) {
            return memberRepository.getMemberAuthorityByRule(filter, pageable);
        } else {
            return memberRepository.findAll(pageable);
        }
    }

    public ResponseEntity<String> setMemberAsLeader(Long memberId) {
        Optional<MemberEntity> optionalMember = memberRepositoryData.findById(memberId);
        return optionalMember.map(o -> {
            o.setMemberRole(MemberRoleState.ROLE_SET_LEADER);
            memberRepositoryData.save(o);
            return ResponseEntity.ok().body("리더로 권한 변경 완료");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**보드의 공통적인 컬럼 BoardUsing의 클래스를 찾고, 그것을 이용해서 JPA 레파지토리의 공통적인 메서드 Save를 활용한 업데이트 메서드*/
    protected <T> ResponseEntity<String> updateBoardUsing(Optional<T> optional, JpaRepository<T, Long> repository) {
        return optional.map(o -> {
            try {
                Class<T> aClass = (Class<T>) o.getClass();
                aClass.getMethod("setBoardUsing", BoardUsing.class).invoke(o, BoardUsing.BLOCK);
                repository.save(o);
                return ResponseEntity.ok().body("보드 비활성화 성공");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당 클래스 내에 BoardUsing을 찾지 못했습니다.");
            }
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
