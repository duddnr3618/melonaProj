package com.fundguide.melona.board.normalBoard.repository;


import com.fundguide.melona.board.common.dto.BoardSearchDTO;
import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.normalBoard.entity.QNormalBoardEntity.normalBoardEntity;
import static com.fundguide.melona.board.normalBoard.entity.QNormalBoardImpeachEntity.normalBoardImpeachEntity;

@Repository
@RequiredArgsConstructor
public class NormalBoardRepositoryImpl implements NormalBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<NormalBoardEntity> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    /**{@inheritDoc}*/
    @Override
    public Page<NormalBoardEntity> findAllViewBoard(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(normalBoardEntity);
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public PageImpl<NormalBoardEntity> searchViewBoard(Pageable pageable, BoardSearchDTO searchDTO) {
        String likeKeyword = "%" + searchDTO.searchKeyword().toLowerCase() + "%";
        String searchOption = searchDTO.searchOption().toLowerCase();

        /*if ("all".equals(searchOption) || searchOption.isEmpty()) {
            booleanExpression = normalBoardEntity.boardWriter.like(likeKeyword)
                    .or(normalBoardEntity.boardTitle.like(likeKeyword))
                    .or(normalBoardEntity.boardContents.like(likeKeyword));
        } else if ("content".equals(searchOption)) {
            booleanExpression = normalBoardEntity.boardContents.like(likeKeyword);
        } else if ("writeruser".equals(searchDTO.searchOption())) {
            booleanExpression = normalBoardEntity.boardWriter.like(likeKeyword);
        } else if ("title".equals(searchDTO.searchOption())) {
            booleanExpression = normalBoardEntity.boardTitle.like(likeKeyword);
        }*/
        return dslRepeatCode.searchKeywordAndOption(normalBoardEntity, booleanExpression, searchDTO.searchKeyword(), pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public Page<NormalBoardEntity> filterViewBoard(Pageable pageable) {
        /*booleanExpression.eq(normalBoardEntity.boardLikes.gt(200));*/
        return null;
    }

    /**{@inheritDoc}*/
    @Override
    public Page<NormalBoardEntity> onlyViewFilterByWaring(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(normalBoardEntity)
                .join(normalBoardImpeachEntity)
                .on(normalBoardImpeachEntity.id.eq(normalBoardImpeachEntity.board.id))
                .where(normalBoardEntity.boardUsing.notIn(BoardUsing.BLOCK)
                        .and(JPAExpressions
                                .select(normalBoardImpeachEntity.board.id.count())
                                .from(normalBoardImpeachEntity)
                                .where(normalBoardEntity.id.eq(normalBoardImpeachEntity.board.id))
                                .groupBy(normalBoardEntity.id)
                                .having(normalBoardEntity.id.count().goe(30))
                                .exists()
                        )
                )
                .distinct();
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public Page<NormalBoardEntity> onlyViewFilterByBlock(Pageable pageable) {
        booleanExpression = normalBoardEntity.boardUsing.in(BoardUsing.BLOCK);
        boardEntityJPAQuery = queryFactory.selectFrom(normalBoardEntity)
                .where(booleanExpression)
                .orderBy(normalBoardEntity.id.desc());
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public Page<NormalBoardEntity> findAll(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(normalBoardEntity)
                .where(normalBoardEntity.boardUsing.notIn(BoardUsing.BLOCK))
                .orderBy(normalBoardEntity.id.desc());
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }
}
