package com.fundguide.melona.management.service.filter;

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
    public Page<?> waringPage(Pageable pageable) {
        return null;
    }

    @Override
    public Page<?> blockPage(Pageable pageable) {
        return null;
    }

    @Override
    public Page<?> allPage(Pageable pageable) {
        return normalBoardRepository.findAll(pageable);
    }
}
