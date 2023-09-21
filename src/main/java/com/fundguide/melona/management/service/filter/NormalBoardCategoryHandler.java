package com.fundguide.melona.management.service.filter;

import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class NormalBoardCategoryHandler implements FilterCategoryHandler {
    private NormalBoardRepository normalBoardRepository;

    @Override
    public Page<?> handleFilterCategory(String filter, Pageable pageable) {
        return FilterCategoryHandler.super.handleFilterCategory(filter, pageable);
    }

    @Override
    public Page<?> waringPage() {
        return null;
    }

    @Override
    public Page<?> blockPage() {
        return null;
    }

    @Override
    public Page<?> allPage() {
        return null;
    }
}
