package com.fundguide.melona.board.leaderboard.service;

import com.fundguide.melona.board.common.dto.ImpeachDTO;
import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardImpeachEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoard_like;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import com.fundguide.melona.board.leaderboard.repository.impeach.LeaderboardImpeachRepository;
import com.fundguide.melona.board.leaderboard.repository.like.LeaderboardLikeRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class LeaderBoardService {

    @Value("${leaderAbsolutePath.dir}")
    private String absolutePath;

    @Value("${leaderResourcePath.dir}")
    private String resourcePath;

    private final LeaderBoardRepository leaderBoardRepository;
    private final MemberRepository memberRepository;
    private final LeaderboardLikeRepository leaderBoardLikeRepository;
    private final LeaderboardImpeachRepository leaderBoardImpeachRepository;
    public void writePro(LeaderBoardDto leaderBoardDto, MultipartFile file) throws Exception {
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
            leaderBoardDto.setFileName(fileName);
            leaderBoardDto.setFilePath(resourcePath + fileName);
        } else {
            System.out.println(" { 파일이 존재하지 않음 보드만 저장함" + " }");
        }

        System.out.println("최종 결과값은? 파일 경로? { " + leaderBoardDto.getFilePath() + " }");
        System.out.println("최종 결과값은? 파일 이름? { " + leaderBoardDto.getFileName() + " }");
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(leaderBoardDto.getMemberId());
        LeaderBoardEntity leaderBoardEntity = LeaderBoardEntity.toSaveLeaderBoardEntity(leaderBoardDto);
        memberEntity.setId(leaderBoardDto.getMemberId());
        leaderBoardEntity.setMemberEntity(memberEntity);

        leaderBoardRepository.save(leaderBoardEntity);
    }

    public Page<LeaderBoardEntity> boardList(Pageable pageable) {
        return leaderBoardRepository.findAll(pageable);
    }

    public Page<LeaderBoardEntity> searchList(String searchKeyword, Pageable pageable) {
        return leaderBoardRepository
                .findByBoardTitleContaining(searchKeyword, pageable);
    }

    @Transactional
    public void updateHits(Long id) {
        leaderBoardRepository.updateHits(id);
    }

    public LeaderBoardDto boardDetail(Long id) {
        Optional<LeaderBoardEntity> optionalLeaderBoard = leaderBoardRepository.findById(id);

        if (optionalLeaderBoard.isPresent()) {
            LeaderBoardEntity leaderBoardEntity = optionalLeaderBoard.get();
            return LeaderBoardDto.toLeaderBoardDto(leaderBoardEntity);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        leaderBoardRepository.deleteById(id);
    }

    @Transactional
    public LeaderBoardDto update(LeaderBoardDto leaderBoardDto, MultipartFile file) {
        System.out.println("! >>>>>>>>>>>>>>> : "+ leaderBoardDto);
        MemberEntity memberEntity = new  MemberEntity();
        memberEntity.setId(leaderBoardDto.getMemberId());
        LeaderBoardEntity leaderBoardEntity = LeaderBoardEntity.toUpdateLeaderBoardEntity(leaderBoardDto);
        memberEntity.setId(leaderBoardDto.getMemberId());
        leaderBoardEntity.setMemberEntity(memberEntity);
        System.out.println("4 >>>>>>>>>>>>>> : " + leaderBoardEntity);
        leaderBoardRepository.save(leaderBoardEntity);
        return boardDetail(leaderBoardDto.getId());
    }

    /*---------------------------------------------------------------------------------------------*/

    /** 신고 서비스 메서드 */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> impeach(Principal principal, ImpeachDTO impeachDTO) {
        MemberEntity memberEntity = memberRepository.findByEmail(principal.getName());
        Optional<LeaderBoardEntity> leaderBoardEntity = leaderBoardRepository.findById(impeachDTO.getId());

        try {
            leaderBoardEntity.ifPresentOrElse(oLeaderBoardEntity -> {
                LeaderBoardImpeachEntity impeach = LeaderBoardImpeachEntity.builder()
                        .member(memberEntity)
                        .board(oLeaderBoardEntity)
                        .cause(impeachDTO.getCause())
                        .build();

                boolean check = leaderBoardImpeachRepository.checkAlreadyImpeach(impeach);
                if (check) {
                    oLeaderBoardEntity.getImpeach().add(impeach);
                    leaderBoardRepository.save(oLeaderBoardEntity);
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
        Optional<LeaderBoardEntity> leaderBoardEntity = leaderBoardRepository.findById(boardId);
        Optional<MemberEntity> member = memberRepository.findByMemberEamilOptional(principal.getName());

        LeaderBoard_like like = null;
        LeaderBoard_like searchLike;
        if (leaderBoardEntity.isPresent() && member.isPresent()) {
            searchLike = LeaderBoard_like.likeFastBuilder(leaderBoardEntity.get(), member.get());
            like = leaderBoardLikeRepository.searchAlreadyLike(searchLike);
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
                leaderBoardEntity.ifPresent(leaderBoard -> {
                    leaderBoard.getBoardLike().add(searchLike);
                    leaderBoardRepository.save(leaderBoard);
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

        LeaderBoard_like like = likeOptionalCheckHandler(principal, boardId);
        BooleanCheck caseCheck = new BooleanCheck() {
            @Override
            public ResponseEntity<String> trueCheck() {
                leaderBoardLikeRepository.removeLike(like);
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

    protected LeaderBoard_like likeOptionalCheckHandler(Principal principal, Long boardId) {
        Optional<LeaderBoardEntity> leaderBoard = leaderBoardRepository.findById(boardId);
        Optional<MemberEntity> member = memberRepository.findByMemberEamilOptional(principal.getName());

        if (leaderBoard.isPresent() && member.isPresent()) {
            LeaderBoard_like like = LeaderBoard_like.builder()
                    .leaderBoardEntity(leaderBoard.get())
                    .memberEntity(member.get())
                    .build();
            return leaderBoardLikeRepository.searchAlreadyLike(like);
        } else {
            throw new IllegalArgumentException("CommunityEntity 또는 MemberEntity가 없어 like 객체를 생성할 수 없습니다.");
        }
    }

    interface BooleanCheck {
        ResponseEntity<String> trueCheck();

        ResponseEntity<String> falseCheck();
    }

}
