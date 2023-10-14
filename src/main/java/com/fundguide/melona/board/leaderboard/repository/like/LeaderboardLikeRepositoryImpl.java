package com.fundguide.melona.board.leaderboard.repository.like;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoard_like;
import com.fundguide.melona.board.normalBoard.entity.NormalBoard_like;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.leaderboard.entity.QLeaderBoard_like.leaderBoard_like;

@Repository
@RequiredArgsConstructor
public class LeaderboardLikeRepositoryImpl implements LeaderboardLikeCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<LeaderBoard_like> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    @Override
    public boolean checkAlreadyLike(LeaderBoard_like like) {
        booleanExpression = likeConsistency(like);
        LeaderBoard_like like1 = queryFactory.selectFrom(leaderBoard_like).where(booleanExpression).fetchOne();
        return like1 != null;
    }

    /** {@inheritDoc} */
    @Override
    public LeaderBoard_like searchAlreadyLike(LeaderBoard_like like) {
        booleanExpression = likeConsistency(like);
        return queryFactory.selectFrom(leaderBoard_like)
                .where(booleanExpression)
                .fetchOne();
    }

    /** {@inheritDoc} */
    @Override
    public void removeLike(LeaderBoard_like like) {
        LeaderBoard_like normalBoardLike = searchAlreadyLike(like);
        boolean check = normalBoardLike != null;

        try {
            if (check) {
                booleanExpression = likeConsistency(like);
                queryFactory.delete(leaderBoard_like)
                        .where(booleanExpression)
                        .execute();
            }
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("존재하지 않는 좋아요 입니다.");
        }
    }

    /**테이블에 기록된것의 정합성을 맞추기 위한 메서드*/
    protected BooleanExpression likeConsistency(LeaderBoard_like like) {
        return leaderBoard_like.leaderBoardEntity.id.eq(like.getLeaderBoardEntity().getId())
                .and(leaderBoard_like.memberEntity.id.eq(like.getMemberEntity().getId()));
    }
}
