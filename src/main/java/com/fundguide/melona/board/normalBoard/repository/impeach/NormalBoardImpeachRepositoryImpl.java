package com.fundguide.melona.board.normalBoard.repository.impeach;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardImpeachEntity;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.normalBoard.entity.QNormalBoardImpeachEntity.normalBoardImpeachEntity;

@Repository
@RequiredArgsConstructor
public class NormalBoardImpeachRepositoryImpl implements NormalBoardImpeachCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<NormalBoardImpeachEntity> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    @Override
    public boolean checkAlreadyImpeach(NormalBoardImpeachEntity impeach) {
        booleanExpression = normalBoardImpeachEntity.board.id.eq(impeach.getBoard().getId())
                .and(normalBoardImpeachEntity.member.id.eq(impeach.getMember().getId()));

        NormalBoardImpeachEntity communityImpeach = queryFactory.selectFrom(normalBoardImpeachEntity).where(booleanExpression).fetchOne();
        return communityImpeach != null;
    }

}
