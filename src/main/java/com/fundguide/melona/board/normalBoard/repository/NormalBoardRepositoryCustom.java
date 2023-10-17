package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.common.dto.BoardSearchDTO;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NormalBoardRepositoryCustom {
    
    /**FindAll 사용시 관계형까지 전부 검색해 새로 만듦*/
    Page<NormalBoardEntity> findAllViewBoard(Pageable pageable);
    
    Page<NormalBoardEntity> searchViewBoard(Pageable pageable, BoardSearchDTO searchDTO);

    /**TODO 구현해야함*/
    Page<NormalBoardEntity> filterViewBoard(Pageable pageable);

    /**비활성화 게시판이 아니면서 신고수 30 이상의 게시판을 조회하는 메서드
     * <p> 경고적인 게시판 조회
     * */
    Page<NormalBoardEntity> onlyViewFilterByWaring(Pageable pageable);

    /**비활성화인 게시판을 조회하는 메서드
     * <p> 제제된 게시판 조회
     * */
    Page<NormalBoardEntity> onlyViewFilterByBlock(Pageable pageable);
    
    Page<NormalBoardEntity> findAll(Pageable pageable);
}
