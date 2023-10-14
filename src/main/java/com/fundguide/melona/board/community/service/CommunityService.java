package com.fundguide.melona.board.community.service;

import com.fundguide.melona.board.common.dto.ImpeachDTO;
import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.community.repository.impeach.CommunityImpeachRepository;
import com.fundguide.melona.board.community.repository.like.CommunityLikeRepository;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
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
    private final MemberRepository memberRepository;

    private final CommunityLikeRepository likeRepository;
    private final CommunityImpeachRepository impeachRepository;
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
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(communityDto.getMemberId());
        CommunityEntity communityEntity = CommunityEntity.toSaveEntity(communityDto);
        memberEntity.setId(communityDto.getMemberId());
        communityEntity.setMemberEntity(memberEntity);

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

    @Transactional
    public CommunityDto update(CommunityDto communityDto, MultipartFile file) {
        MemberEntity memberEntity = new  MemberEntity();
        memberEntity.setId(communityDto.getMemberId());
        CommunityEntity communityEntity = CommunityEntity.toUpdateEntity(communityDto);
        memberEntity.setId(communityDto.getMemberId());
        communityEntity.setMemberEntity(memberEntity);

        System.out.println("4 >>>>>>>>>>>>>> : " + communityEntity.getMemberEntity().getId());
        communityRepository.save(communityEntity);
        return boardDetail(communityDto.getId());
    }

    /*---------------------------------------------------------------------------------------------*/

    /** 신고 서비스 메서드 */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> impeach(Principal principal, ImpeachDTO impeachDTO) {
        MemberEntity memberEntity = memberRepository.findByEmail(principal.getName());
        Optional<CommunityEntity> communityEntity = communityRepository.findById(impeachDTO.getId());

        try {
            communityEntity.ifPresentOrElse(oCommunityEntity -> {
                CommunityImpeachEntity impeach = CommunityImpeachEntity.builder()
                        .member(memberEntity)
                        .board(oCommunityEntity)
                        .cause(impeachDTO.getCause())
                        .build();

                boolean check = impeachRepository.checkAlreadyImpeach(impeach);
                if (check) {
                    oCommunityEntity.getImpeach().add(impeach);
                    communityRepository.save(oCommunityEntity);
                    ResponseEntity.badRequest().build();
                }
            }, () -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
            System.out.println(" { 신고 성공" + " }");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).build();
        }
    }



    /** 좋아요 추가 서비스 메서드 */
    @Transactional
    public ResponseEntity<String> likeAdd(Principal principal, Long boardId) {
        Optional<CommunityEntity> community = communityRepository.findById(boardId);
        Optional<MemberEntity> member = memberRepository.findByMemberEamilOptional(principal.getName());

        Community_like like = null;
        Community_like searchLike;
        if (community.isPresent() && member.isPresent()) {
            searchLike = Community_like.likeFastBuilder(community.get(), member.get());
            like = likeRepository.searchAlreadyLike(searchLike);
        } else {
            throw new IllegalArgumentException("CommunityEntity 또는 MemberEntity를 찾지 못해 like 객체를 생성할 수 없습니다.");
        }

        BooleanCheck caseCheck = new BooleanCheck() {
            @Override
            public ResponseEntity<String> trueCheck() {
                return ResponseEntity.badRequest().build();
            }

            @Override
            public ResponseEntity<String> falseCheck() {
                community.ifPresent(communityEntity -> {
                    communityEntity.getBoardLike().add(searchLike);
                    communityRepository.save(communityEntity);
                });
                return ResponseEntity.ok().build();
            }
        };

        boolean check = like != null;
        if (check) {
            return caseCheck.trueCheck();
        } else {
            return caseCheck.falseCheck();
        }
    }

    /** 좋아요 삭제 서비스 메서드 */
    @Transactional
    public ResponseEntity<String> likeRemove(Principal principal, Long boardId) {

        Community_like like = likeOptionalCheckHandler(principal, boardId);
        BooleanCheck caseCheck = new BooleanCheck() {
            @Override
            public ResponseEntity<String> trueCheck() {
                likeRepository.removeLike(like);
                return ResponseEntity.ok().build();
            }

            @Override
            public ResponseEntity<String> falseCheck() {
                return ResponseEntity.badRequest().build();
            }
        };

        boolean check = like != null;
        if (check) {
            return caseCheck.trueCheck();
        } else {
            return caseCheck.falseCheck();
        }
    }

    protected Community_like likeOptionalCheckHandler(Principal principal, Long boardId) {
        Optional<CommunityEntity> community = communityRepository.findById(boardId);
        Optional<MemberEntity> member = memberRepository.findByMemberEamilOptional(principal.getName());

        if (community.isPresent() && member.isPresent()) {
            Community_like like = Community_like.builder()
                    .communityEntity(community.get())
                    .memberEntity(member.get())
                    .build();
            return likeRepository.searchAlreadyLike(like);
        } else {
            throw new IllegalArgumentException("CommunityEntity 또는 MemberEntity가 없어 like 객체를 생성할 수 없습니다.");
        }
    }

    interface BooleanCheck {
        ResponseEntity<String> trueCheck();

        ResponseEntity<String> falseCheck();
    }
}
