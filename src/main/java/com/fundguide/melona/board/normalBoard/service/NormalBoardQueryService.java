package com.fundguide.melona.board.normalBoard.service;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional(readOnly = true)
public class NormalBoardQueryService {
    private final NormalBoardRepository normalBoardRepository;

    public Page<NormalBoardEntity> onlyViewNormalBoard(Pageable pageable) {
        return normalBoardRepository.onlyViewNormalBoard(pageable);
    }
}
