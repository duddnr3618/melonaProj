package com.fundguide.melona.board.community.service;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    

    public void writePro(CommunityDto communityDto) {
        CommunityEntity communityEntity = CommunityEntity.of(communityDto);
        communityRepository.save(communityEntity);
    }
    public Page<CommunityDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageLimit = 10; // 페이지당 보여질 게시물 수

        Page<CommunityEntity> communityEntities = communityRepository.findAll(
                PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createdTime")));

        // 페이지 정보 출력 (옵션)
        System.out.println("communityEntities.getContent() = " + communityEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("communityEntities.getTotalElements() = " + communityEntities.getTotalElements()); // 전체 글 갯수
        System.out.println("communityEntities.getNumber() = " + communityEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("communityEntities.getTotalPages() = " + communityEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("communityEntities.getSize() = " + communityEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("communityEntities.hasPrevious() = " + communityEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("communityEntities.isFirst() = " + communityEntities.isFirst()); // 첫 페이지 여부
        System.out.println("communityEntities.isLast() = " + communityEntities.isLast()); // 마지막 페이지 여부

        // CommunityEntity를 CommunityDto로 변환
        Page<CommunityDto> communityDtos = communityEntities.map(communityEntity -> {
            CommunityDto communityDto = new CommunityDto();
            communityDto.setId(communityEntity.getId());
            communityDto.setBoardTitle(communityEntity.getBoardTitle());
            communityDto.setBoardContents(communityEntity.getBoardContents());
            communityDto.setBoardHits(communityEntity.getBoardHits());
            communityDto.setCreatedTime(communityEntity.getCreatedTime());
            return communityDto;
        });

        return communityDtos;
    }
}
