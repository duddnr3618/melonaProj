package com.fundguide.melona.board.leaderBoard;

import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import com.fundguide.melona.board.community.repository.impeach.CommunityImpeachRepository;
import com.fundguide.melona.board.community.repository.like.CommunityLikeRepository;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardImpeachEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoard_like;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import com.fundguide.melona.board.leaderboard.repository.impeach.LeaderboardImpeachRepository;
import com.fundguide.melona.board.leaderboard.repository.like.LeaderboardLikeRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
class leaderBoardEntityTest {

    @Autowired
    private LeaderBoardRepository repository;

    @Autowired
    private LeaderboardImpeachRepository impeachRepository;

    @Autowired
    private LeaderboardLikeRepository likeRepository;

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
                LeaderBoardEntity normalBoard = LeaderBoardEntity.builder()
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
            Optional<LeaderBoardEntity> entity = repository.findById(random.nextLong(100));
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
        Optional<LeaderBoardEntity> optionalNormalBoard = null;
        for (long l : longArray) {
            optionalNormalBoard = repository.findById(l);

            for (long t : longArray) {
                optionalMember = memberRepositoryData.findById(random.nextLong(100));
                if (optionalMember.isPresent() && optionalNormalBoard.isPresent()) {

                    LeaderBoardImpeachEntity impeach = LeaderBoardImpeachEntity.builder()
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
        long[] longArray = new long[10];
        for (long l = 0; l < 10; l++) {
            longArray[(int) l] = random.nextLong(100);
        }

        Optional<MemberEntity> optionalMember = Optional.empty();
        Optional<LeaderBoardEntity> optionalNormalBoard = Optional.empty();
        for (long l : longArray) {
            optionalNormalBoard = repository.findById(l);

            for (long t : longArray) {
                optionalMember = memberRepositoryData.findById(random.nextLong(100));
                if (optionalMember.isPresent() && optionalNormalBoard.isPresent()) {
                    LeaderBoard_like impeach = LeaderBoard_like.builder()
                            .memberEntity(optionalMember.get())
                            .leaderBoardEntity(optionalNormalBoard.get())
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