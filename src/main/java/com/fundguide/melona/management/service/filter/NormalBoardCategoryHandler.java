package com.fundguide.melona.management.service.filter;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class NormalBoardCategoryHandler implements FilterCategoryHandler {
    private final NormalBoardRepository normalBoardRepository;

    @Override
    public Page<?> handleFilterCategory(String filter, Pageable pageable) {
        return FilterCategoryHandler.super.handleFilterCategory(filter, pageable);
    }

    @Override
    public Page<NormalBoardEntity> waringPage(Pageable pageable) {
        return normalBoardRepository.onlyViewNormalBoardFilterByWaring(pageable);
    }

    @Override
    public Page<NormalBoardEntity> blockPage(Pageable pageable) {
        return normalBoardRepository.onlyViewNormalBoardFilterByBlock(pageable);
    }

    @Override
    public Page<NormalBoardEntity> allPage(Pageable pageable) {
        return normalBoardRepository.findAllViewBoard(pageable);
    }
}
