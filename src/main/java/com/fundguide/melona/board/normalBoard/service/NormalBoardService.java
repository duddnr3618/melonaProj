package com.fundguide.melona.board.normalBoard.service;

import com.fundguide.melona.board.common.dto.ImpeachDTO;
import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardImpeachEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoard_like;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import com.fundguide.melona.board.normalBoard.repository.impeach.NormalBoardImpeachRepository;
import com.fundguide.melona.board.normalBoard.repository.like.NormalBoardLikeRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class NormalBoardService {

    @Value("normalAbsolutePath.dir")
    private String absolutePath;

    @Value("normalResourcePath.dir")
    private String resourcePath;

    private final NormalBoardRepository normalBoardRepository;
    private final MemberRepository memberRepository;
    private final NormalBoardLikeRepository normalBoardLikeRepository;
    private final NormalBoardImpeachRepository normalBoardImpeachRepository;
    public void writePro(NormalBoardDto normalBoardDto, MultipartFile file) throws Exception {
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
            normalBoardDto.setFileName(fileName);
            normalBoardDto.setFilePath(resourcePath + fileName);
        } else {
            System.out.println(" { 파일이 존재하지 않음 보드만 저장함" + " }");
        }

        System.out.println("최종 결과값은? 파일 경로? { " + normalBoardDto.getFilePath() + " }");
        System.out.println("최종 결과값은? 파일 이름? { " + normalBoardDto.getFileName() + " }");
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(normalBoardDto.getMemberId());
        NormalBoardEntity normalBoardEntity = NormalBoardEntity.toSaveNormalBoardEntity(normalBoardDto);
        memberEntity.setId(normalBoardDto.getMemberId());
        normalBoardEntity.setMemberEntity(memberEntity);

        normalBoardRepository.save(normalBoardEntity);
    }

    public Page<NormalBoardEntity> boardList(Pageable pageable) {
        return normalBoardRepository.findAll(pageable);
    }

    public Page<NormalBoardEntity> searchList(String searchKeyword, Pageable pageable) {
        return normalBoardRepository
                .findByBoardTitleContaining(searchKeyword, pageable);
    }

    @Transactional
    public void updateHits(Long id) {
        normalBoardRepository.updateHits(id);
    }

    public NormalBoardDto boardDetail(Long id) {
        Optional<NormalBoardEntity> optionalNormalBoardEntity = normalBoardRepository.findById(id);

        if (optionalNormalBoardEntity.isPresent()) {
            NormalBoardEntity normalBoardEntity = optionalNormalBoardEntity.get();
            return NormalBoardDto.toNormalBoardDto(normalBoardEntity);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        normalBoardRepository.deleteById(id);
    }

    @Transactional
    public NormalBoardDto update(NormalBoardDto normalBoardDto, MultipartFile file) {
        MemberEntity memberEntity = new  MemberEntity();
        memberEntity.setId(normalBoardDto.getMemberId());
        NormalBoardEntity normalBoardEntity = NormalBoardEntity.toUpdateNormalBoardEntity(normalBoardDto);
        memberEntity.setId(normalBoardDto.getMemberId());
        normalBoardEntity.setMemberEntity(memberEntity);
        System.out.println("4 >>>>>>>>>>>>>> : " + normalBoardEntity.getMemberEntity().getId());
        normalBoardRepository.save(normalBoardEntity);
        return boardDetail(normalBoardDto.getId());
    }

    /*---------------------------------------------------------------------------------------------*/

    /** 신고 서비스 메서드 */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> impeach(Principal principal, ImpeachDTO impeachDTO) {
        MemberEntity memberEntity = memberRepository.findByEmail(principal.getName());
        Optional<NormalBoardEntity> normalBoardEntity = normalBoardRepository.findById(impeachDTO.getId());

        try {
            normalBoardEntity.ifPresentOrElse(oNormalBoardEntity -> {
                NormalBoardImpeachEntity impeach = NormalBoardImpeachEntity.builder()
                        .member(memberEntity)
                        .board(oNormalBoardEntity)
                        .cause(impeachDTO.getCause())
                        .build();

                boolean check = normalBoardImpeachRepository.checkAlreadyImpeach(impeach);
                if (check) {
                    oNormalBoardEntity.getImpeach().add(impeach);
                    normalBoardRepository.save(oNormalBoardEntity);
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
        Optional<NormalBoardEntity> normalBoardEntity = normalBoardRepository.findById(boardId);
        Optional<MemberEntity> member = memberRepository.findByMemberEamilOptional(principal.getName());

        NormalBoard_like like = null;
        NormalBoard_like searchLike;
        if (normalBoardEntity.isPresent() && member.isPresent()) {
            searchLike = NormalBoard_like.likeFastBuilder(normalBoardEntity.get(), member.get());
            like = normalBoardLikeRepository.searchAlreadyLike(searchLike);
        } else {
            throw new IllegalArgumentException("CommunityEntity 또는 MemberEntity를 찾지 못해 like 객체를 생성할 수 없습니다.");
        }

        NormalBoardService.BooleanCheck caseCheck = new NormalBoardService.BooleanCheck() {
            @Override
            public ResponseEntity<String> trueCheck() {
                return ResponseEntity.badRequest().build();
            }

            @Override
            public ResponseEntity<String> falseCheck() {
                normalBoardEntity.ifPresent(normalBoard -> {
                    normalBoard.getBoardLike().add(searchLike);
                    normalBoardRepository.save(normalBoard);
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

        NormalBoard_like like = likeOptionalCheckHandler(principal, boardId);
        NormalBoardService.BooleanCheck caseCheck = new NormalBoardService.BooleanCheck() {
            @Override
            public ResponseEntity<String> trueCheck() {
                normalBoardLikeRepository.removeLike(like);
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

    protected NormalBoard_like likeOptionalCheckHandler(Principal principal, Long boardId) {
        Optional<NormalBoardEntity> normalBoard = normalBoardRepository.findById(boardId);
        Optional<MemberEntity> member = memberRepository.findByMemberEamilOptional(principal.getName());

        if (normalBoard.isPresent() && member.isPresent()) {
            NormalBoard_like like = NormalBoard_like.builder()
                    .normalBoardEntity(normalBoard.get())
                    .memberEntity(member.get())
                    .build();
            return normalBoardLikeRepository.searchAlreadyLike(like);
        } else {
            throw new IllegalArgumentException("NormalBoardEntity 또는 MemberEntity가 없어 like 객체를 생성할 수 없습니다.");
        }
    }

    interface BooleanCheck {
        ResponseEntity<String> trueCheck();

        ResponseEntity<String> falseCheck();
    }
}
