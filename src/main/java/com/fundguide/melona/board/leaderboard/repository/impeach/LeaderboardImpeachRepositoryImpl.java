package com.fundguide.melona.board.leaderboard.repository.impeach;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardImpeachEntity;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.leaderboard.entity.QLeaderBoardImpeachEntity.leaderBoardImpeachEntity;

@Repository
@RequiredArgsConstructor
public class LeaderboardImpeachRepositoryImpl implements LeaderboardImpeachCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<LeaderBoardImpeachEntity> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    @Override
    public boolean checkAlreadyImpeach(LeaderBoardImpeachEntity impeach) {
        booleanExpression = leaderBoardImpeachEntity.board.id.eq(impeach.getBoard().getId())
                .and(leaderBoardImpeachEntity.member.id.eq(impeach.getMember().getId()));

        LeaderBoardImpeachEntity impeachEntity = queryFactory.selectFrom(leaderBoardImpeachEntity).where(booleanExpression).fetchOne();
        return impeachEntity != null;
    }

}
