package com.fundguide.melona.board.community.service;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    // memberId에 해당하는 사용자가 작성한 게시글을 최신순으로 가져오는 메서드
    public List<CommunityDto> getMyPagePostsOrderByCreatedAt(Long memberId) {
        List<CommunityEntity> posts = communityRepository.findMyPagePostsByMemberIdOrderByCreatedAt(memberId);
        return posts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // memberId에 해당하는 사용자가 작성한 게시글을 좋아요가 많은 순으로 가져오는 메서드
    public List<CommunityDto> getMyPagePostsOrderByLikes(Long memberId) {
        List<CommunityEntity> posts = communityRepository.findMyPagePostsByMemberIdOrderByLikes(memberId);
        return posts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // CommunityEntity를 CommunityDto로 변환하는 메서드 (코드 내에서 사용)
    private CommunityDto convertToDto(CommunityEntity entity) {
        CommunityDto dto = new CommunityDto();
        dto.setId(entity.getId());
        dto.setBoardTitle(entity.getBoardTitle());
        dto.setBoardContents(entity.getBoardContents());
        dto.setBoardHits(entity.getBoardHits());
        dto.setCreatedTime(entity.getCreatedTime());
        dto.setUpdatedTime(entity.getUpdatedTime());
        return dto;
    }
}