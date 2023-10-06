package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.common.dto.BoardSearchDTO;
import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Objects;

public interface NormalBoardRepositoryCustom {
    
    /**FindAll 사용시 관계형까지 전부 검색해 새로 만듦*/
    Page<NormalBoardEntity> findAllViewBoard(Pageable pageable);
    
    Page<NormalBoardEntity> searchViewBoard(Pageable pageable, BoardSearchDTO searchDTO);

    /**TODO 구현해야함*/
    Page<NormalBoardEntity> filterViewBoard(Pageable pageable);

    /**비활성화 게시판이 아니면서 신고수 100 이상의 게시판을 조회하는 메서드
     * <p> 경고적인 게시판 조회
     * */
    Page<NormalBoardEntity> onlyViewNormalBoardFilterByWaring(Pageable pageable);

    /**비활성화인 게시판을 조회하는 메서드
     * <p> 제제된 게시판 조회
     * */
    Page<NormalBoardEntity> onlyViewNormalBoardFilterByBlock(Pageable pageable);

    /**id값으로 노말보드의 단일값을 가져오는 메서드*/
    NormalBoardDto detailNormalBoard(Long id);
}
