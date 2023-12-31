package com.fundguide.melona.board.community.repository;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityRepositoryCustom {

    /**비활성화 게시판이 아니면서 신고수 30 이상의 게시판을 조회하는 메서드
     * <p> 경고적인 게시판 조회
     * */
    Page<CommunityEntity> onlyViewFilterByWaring(Pageable pageable);

    /**비활성화인 게시판을 조회하는 메서드
     * <p> 제제된 게시판 조회
     * */
    Page<CommunityEntity> onlyViewFilterByBlock(Pageable pageable);

    /**impeach만 저장하기 위한 메서드*/
    void impeachSave(CommunityEntity entity);

    /**FindAll의 재정의*/
    Page<CommunityEntity> findAll(Pageable pageable);

    /**좋아요 총수*/
    long likeCount(long boardId);
}
