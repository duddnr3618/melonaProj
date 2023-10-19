package com.fundguide.melona.management.service.filter;

import com.fundguide.melona.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CommunityBoardCategoryHandler implements FilterCategoryHandler {
    private final CommunityRepository communityRepository;

    @Override
    public Page<?> handleFilterCategory(String filter, Pageable pageable) {
        return FilterCategoryHandler.super.handleFilterCategory(filter, pageable);
    }

    @Override
    public Page<?> waringPage(Pageable pageable) {
        return communityRepository.onlyViewFilterByWaring(pageable);
    }

    @Override
    public Page<?> blockPage(Pageable pageable) {
        return communityRepository.onlyViewFilterByBlock(pageable);
    }

    @Override
    public Page<?> allPage(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }
}
