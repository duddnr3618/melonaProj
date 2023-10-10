package com.fundguide.melona.board.community.service;

import ch.qos.logback.classic.net.SyslogAppender;
import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.PerFromSuper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommunityService {
    @Value("${communityAbsolutePath.dir}")
    private String absolutePath;

    @Value("${communityResourcePath.dir}")
    private String resourcePath;

    private final CommunityRepository communityRepository;

    public void writePro(CommunityDto communityDto, MultipartFile file) throws Exception {
        System.out.println(" { 커뮤니티 파일 저장중" + " }");
        System.out.println("절대경로는? { " + absolutePath + " }");
        System.out.println("리소스경로는? { " + resourcePath + " }");

        if (!file.isEmpty()) {
            System.out.println(" { 파일 존재함 메서드 시작" + " }");
            String projectPath = absolutePath;
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            communityDto.setFileName(fileName);
            communityDto.setFilePath(resourcePath + fileName);
        } else {
            System.out.println(" { 파일이 존재하지 않음 보드만 저장함" + " }");
        }

        System.out.println("최종 결과값은? 파일 경로? { " + communityDto.getFilePath() + " }");
        System.out.println("최종 결과값은? 파일 이름? { " + communityDto.getFileName() + " }");

        CommunityEntity communityEntity = CommunityEntity.toSaveEntity(communityDto);
        communityRepository.save(communityEntity);
    }

    public Page<CommunityEntity> boardList(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }

    public Page<CommunityEntity> searchList(String searchKeyword, Pageable pageable) {
        return communityRepository
                .findByBoardTitleContaining(searchKeyword, pageable);
    }

    @Transactional
    public void updateHits(Long id) {
        communityRepository.updateHits(id);
    }

    public CommunityDto boardDetail(Long id) {
        Optional<CommunityEntity> optionalCommunityEntity = communityRepository.findById(id);
        if (optionalCommunityEntity.isPresent()) {
            CommunityEntity communityEntity = optionalCommunityEntity.get();
            return CommunityDto.toBoardDto(communityEntity);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        communityRepository.deleteById(id);
    }

    public CommunityDto update(CommunityDto communityDto, MultipartFile file) {
        CommunityEntity communityEntity = CommunityEntity.toUpdateEntity(communityDto);
        communityRepository.save(communityEntity);
        return boardDetail(communityDto.getId());
    }


}