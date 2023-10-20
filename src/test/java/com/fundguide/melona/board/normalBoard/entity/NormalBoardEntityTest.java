package com.fundguide.melona.board.normalBoard.entity;

import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import com.fundguide.melona.board.normalBoard.repository.impeach.NormalBoardImpeachRepository;
import com.fundguide.melona.board.normalBoard.repository.like.NormalBoardLikeRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
class NormalBoardEntityTest {

    @Autowired
    private NormalBoardRepository repository;

    @Autowired
    private NormalBoardImpeachRepository impeachRepository;

    @Autowired
    private NormalBoardLikeRepository likeRepository;

    @Autowired
    private MemberRepositoryData memberRepositoryData;

    Random random = new Random();

    @Test
    void dummy() {
        Random random = new Random();
        for (long i = 0; i < 100; i++) {
            Optional<MemberEntity> memberEntity = memberRepositoryData.findById(random.nextLong(100));
            long finalI = i;

            BoardUsing[] boardUsing = {BoardUsing.USING, BoardUsing.BLOCK};
            memberEntity.ifPresent(o -> {
                NormalBoardEntity normalBoard = NormalBoardEntity.builder()
                        .memberEntity(o)
                        .boardTitle("NormalTitle " + finalI)
                        .boardContents("NormalCon " + finalI)
                        .boardHits(random.nextInt(10))
                        .boardUsing(boardUsing[random.nextInt(1)])
                        .build();
                repository.save(normalBoard);
            });
        }
    }

    @Test
    void randomUsing() {
        Random random = new Random();
        BoardUsing[] boardUsing = {BoardUsing.USING, BoardUsing.BLOCK};
        for (long i = 0; i < 30; i++) {
            Optional<NormalBoardEntity> entity = repository.findById(random.nextLong(100));
            entity.ifPresent(normalBoardEntity -> {
                normalBoardEntity.setBoardUsing(boardUsing[random.nextInt(2)]);
                repository.save(normalBoardEntity);
            });
        }
    }

    @Test
    void impeachDummy() {
        long[] longArray = new long[10];
        for (long l = 0; l < 1000; l++) {
            longArray[(int) l] = random.nextLong(100);
        }

        Optional<MemberEntity> optionalMember = null;
        Optional<NormalBoardEntity> optionalNormalBoard = null;
        for (long l : longArray) {
            optionalNormalBoard = repository.findById(l);

            for (long t : longArray) {
                optionalMember = memberRepositoryData.findById(random.nextLong(100));
                if (optionalMember.isPresent() && optionalNormalBoard.isPresent()) {
                    NormalBoardImpeachEntity impeach = NormalBoardImpeachEntity.builder()
                            .board(optionalNormalBoard.get())
                            .member(optionalMember.get())
                            .cause("노말보드 신고 테스트" + l)
                            .build();

                    boolean check = impeachRepository.checkAlreadyImpeach(impeach);
                    if (!check) {
                        impeachRepository.save(impeach);
                    }
                }
            }
        }
    }

    @Test
    void likeDummy() {
        long[] longArray = new long[1000];
        for (long l = 0; l < 1000; l++) {
            longArray[(int) l] = random.nextLong(10000);
        }

        Optional<MemberEntity> optionalMember = Optional.empty();
        Optional<NormalBoardEntity> optionalNormalBoard = Optional.empty();
        for (long l : longArray) {
            optionalNormalBoard = repository.findById(l);

            for (long t : longArray) {
                optionalMember = memberRepositoryData.findById(random.nextLong(10000));
                if (optionalMember.isPresent() && optionalNormalBoard.isPresent()) {
                    NormalBoard_like impeach = NormalBoard_like.builder()
                            .memberEntity(optionalMember.get())
                            .normalBoardEntity(optionalNormalBoard.get())
                            .build();

                    boolean check = likeRepository.checkAlreadyLike(impeach);
                    if (!check) {
                        likeRepository.save(impeach);
                    }
                }
            }
        }
    }
}