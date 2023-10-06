package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.common.dto.BoardSearchDTO;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NormalBoardRepositoryCustom {
    Page<NormalBoardEntity> searchViewBoard(Pageable pageable, BoardSearchDTO searchDTO);
    Page<NormalBoardEntity> filterViewBoard(Pageable pageable);
}
