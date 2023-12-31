package com.fundguide.melona.board.community.repository;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fundguide.melona.board.community.entity.QCommunityEntity.communityEntity;
import static com.fundguide.melona.board.community.entity.QCommunityImpeachEntity.communityImpeachEntity;
import static com.fundguide.melona.board.community.entity.QCommunity_like.community_like;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<CommunityEntity> boardEntityJPAQuery = null;

    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    private final EntityManager entityManager;


    /** {@inheritDoc} */
    @Override
    public Page<CommunityEntity> onlyViewFilterByWaring(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(communityEntity)
                .join(communityImpeachEntity)
                .on(communityEntity.id.eq(communityImpeachEntity.board.id))
                .where(communityEntity.boardUsing.notIn(BoardUsing.BLOCK)
                        .and(JPAExpressions
                                .select(communityImpeachEntity.board.id.count())
                                .from(communityImpeachEntity)
                                .where(communityEntity.id.eq(communityImpeachEntity.board.id))
                                .groupBy(communityEntity.id)
                                .having(communityEntity.id.count().goe(30))
                                .exists()
                        )
                )
                .distinct();
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /** {@inheritDoc} */
    @Override
    public Page<CommunityEntity> onlyViewFilterByBlock(Pageable pageable) {
        booleanExpression = communityEntity.boardUsing.in(BoardUsing.BLOCK);
        boardEntityJPAQuery = queryFactory.selectFrom(communityEntity).where(booleanExpression);
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /** {@inheritDoc} */
    @Override
    public void impeachSave(CommunityEntity entity) {
        queryFactory.update(communityEntity)
                .set(communityEntity.impeach, entity.getImpeach())
                .where(communityEntity.id.eq(entity.getId()))
                .execute();
    }

    @Override
    public Page<CommunityEntity> findAll(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(communityEntity)
                .where(communityEntity.boardUsing.notIn(BoardUsing.BLOCK))
                .orderBy(communityEntity.id.desc());
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    @Override
    public long likeCount(long boardId) {
        List<CommunityEntity> entities = queryFactory.selectFrom(communityEntity)
                .join(community_like)
                .on(communityEntity.id.eq(community_like.communityEntity.id))
                .fetch();
        return entities.size();
    }
}
