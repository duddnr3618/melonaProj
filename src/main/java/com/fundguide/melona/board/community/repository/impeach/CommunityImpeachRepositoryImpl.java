package com.fundguide.melona.board.community.repository.impeach;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.community.repository.like.CommunityLikeCustom;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.community.entity.QCommunityImpeachEntity.communityImpeachEntity;

@Repository
@RequiredArgsConstructor
public class CommunityImpeachRepositoryImpl implements CommunityImpeachCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<CommunityImpeachEntity> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    @Override
    public boolean checkAlreadyImpeach(CommunityImpeachEntity impeach) {
        booleanExpression = communityImpeachEntity.board.id.eq(impeach.getBoard().getId())
                .and(communityImpeachEntity.member.id.eq(impeach.getMember().getId()));

        CommunityImpeachEntity communityImpeach = queryFactory.selectFrom(communityImpeachEntity).where(booleanExpression).fetchOne();
        return communityImpeach != null;
    }

}
