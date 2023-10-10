package com.fundguide.melona.management.service.filter;

import com.fundguide.melona.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
//TODO waring block 완성할것
public class CommunityBoardCategoryHandler implements FilterCategoryHandler {
    private final CommunityRepository communityRepository;

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
        return communityRepository.findAll(pageable);
    }
}
