package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NormalBoardRepository extends JpaRepository<NormalBoardEntity, Long>, NormalBoardRepositoryCustom {
    @Query("select nbe from NormalBoardEntity nbe")
    Page<NormalBoardEntity> onlyViewNormalBoard(Pageable pageable);
    NormalBoardEntity findAllByBoardId(Long boardId);
}
