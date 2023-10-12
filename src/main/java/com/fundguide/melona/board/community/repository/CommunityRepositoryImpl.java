package com.fundguide.melona.board.community.repository;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.community.entity.QCommunityEntity.communityEntity;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<CommunityEntity> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;


    /**{@inheritDoc}*/
    @Override
    public Page<CommunityEntity> onlyViewNormalBoardFilterByWaring(Pageable pageable) {
        /*booleanExpression = communityEntity.boardUsing.notIn(BoardUsing.BLOCK)
                        .and(communityEntity.impeach.size().goe(100));*/
        boardEntityJPAQuery = queryFactory.selectFrom(communityEntity).where(booleanExpression);
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public Page<CommunityEntity> onlyViewNormalBoardFilterByBlock(Pageable pageable) {
        /*booleanExpression = communityEntity.boardUsing.in(BoardUsing.BLOCK);*/
        boardEntityJPAQuery = queryFactory.selectFrom(communityEntity).where(booleanExpression);
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public void impeachSave(CommunityEntity entity) {
        queryFactory
                .update(communityEntity)
                .set(communityEntity.impeach, entity.getImpeach())
                .where(communityEntity.id.eq(entity.getId()));
    }
}
