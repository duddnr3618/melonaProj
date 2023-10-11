package com.fundguide.melona.board.community.repository.like;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.community.entity.QCommunity_like.community_like;

@Repository
@RequiredArgsConstructor
public class CommunityLikeRepositoryImpl implements CommunityLikeCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<Community_like> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    @Override
    public boolean checkAlreadyLike(Community_like like) {
        booleanExpression = community_like.communityEntity.id.eq(like.getCommunityEntity().getId())
                .and(community_like.memberEntity.id.eq(like.getMemberEntity().getId()));
        Community_like like1 = queryFactory.selectFrom(community_like).where(booleanExpression).fetchOne();
        return like1 != null;
    }

    /**{@inheritDoc}*/
    @Override
    public Community_like like(Community_like like) {
        booleanExpression = community_like.communityEntity.id.eq(like.getCommunityEntity().getId())
                .and(community_like.memberEntity.id.eq(like.getMemberEntity().getId()));
        return queryFactory.selectFrom(community_like).where(booleanExpression).fetchOne();
    }
}
