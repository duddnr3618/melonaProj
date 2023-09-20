package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.dto.CommonBoardSearchDTO;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NormalBoardRepositoryCustom {
    Page<NormalBoardEntity> searchViewBoard(Pageable pageable, CommonBoardSearchDTO searchDTO);
}
