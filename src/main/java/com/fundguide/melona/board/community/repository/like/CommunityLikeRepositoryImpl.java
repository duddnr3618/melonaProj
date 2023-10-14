package com.fundguide.melona.board.community.repository.like;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        booleanExpression = likeConsistency(like);
        Community_like like1 = queryFactory.selectFrom(community_like).where(booleanExpression).fetchOne();
        return like1 != null;
    }

    /** {@inheritDoc} */
    @Override
    public Community_like searchAlreadyLike(Community_like like) {
        booleanExpression = likeConsistency(like);
        return queryFactory.selectFrom(community_like)
                .where(booleanExpression)
                .fetchOne();
    }

    /** {@inheritDoc} */
    @Override
    public void removeLike(Community_like like) {
        Community_like communityLike = searchAlreadyLike(like);
        boolean check = communityLike != null;

        try {
            if (check) {
                booleanExpression = likeConsistency(like);
                queryFactory.delete(community_like)
                        .where(booleanExpression)
                        .execute();
            }
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("존재하지 않는 좋아요 입니다.");
        }
    }

    /**테이블에 기록된것의 정합성을 맞추기 위한 메서드*/
    protected BooleanExpression likeConsistency(Community_like like) {
        return community_like.communityEntity.id.eq(like.getCommunityEntity().getId())
                .and(community_like.memberEntity.id.eq(like.getMemberEntity().getId()));
    }
}
