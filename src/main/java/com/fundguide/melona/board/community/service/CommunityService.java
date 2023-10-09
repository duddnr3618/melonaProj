package com.fundguide.melona.board.community.service;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    

    public void writePro(CommunityDto communityDto) {
        CommunityEntity communityEntity = CommunityEntity.of(communityDto);
        communityRepository.save(communityEntity);
    }

    public Page<CommunityEntity> boardList(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }

    public Page<CommunityEntity> searchList(String searchKeyword, Pageable pageable){
        return communityRepository
                .findByBoardTitleContaining(searchKeyword, pageable);
    }
    @Transactional
    public void updateHits(Long id) {
        communityRepository.updateHits(id);
    }

    public CommunityDto boardDetail(Long id) {
        Optional<CommunityEntity> optionalCommunityEntity = communityRepository.findById(id);
        if(optionalCommunityEntity.isPresent()){
            CommunityEntity communityEntity = optionalCommunityEntity.get();
            CommunityDto communityDto = CommunityDto.of(communityEntity);
            return communityDto;
        }else {
            return null;
        }
    }

    public void delete(Long id) {
        communityRepository.deleteById(id);
    }

    public CommunityDto update(CommunityDto communityDto) {
        CommunityEntity communityEntity = CommunityEntity.of(communityDto);
        communityRepository.save(communityEntity);
        return boardDetail(communityDto.getId());
    }
}