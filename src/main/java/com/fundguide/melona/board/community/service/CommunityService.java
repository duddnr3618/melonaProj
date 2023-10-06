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
    

    public void writePro(CommunityDto communityDto) {
        CommunityEntity communityEntity = CommunityEntity.of(communityDto);
        communityRepository.save(communityEntity);
    }
}