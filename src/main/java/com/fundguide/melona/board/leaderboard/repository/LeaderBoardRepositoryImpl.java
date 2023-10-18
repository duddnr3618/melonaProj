package com.fundguide.melona.board.leaderboard.repository;


import com.fundguide.melona.board.common.dto.BoardSearchDTO;
import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
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

import static com.fundguide.melona.board.leaderboard.entity.QLeaderBoardEntity.leaderBoardEntity;
import static com.fundguide.melona.board.leaderboard.entity.QLeaderBoardImpeachEntity.leaderBoardImpeachEntity;

@Repository
@RequiredArgsConstructor
public class LeaderBoardRepositoryImpl implements LeaderBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<LeaderBoardEntity> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    /**{@inheritDoc}*/
    @Override
    public Page<LeaderBoardEntity> findAllViewBoard(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(leaderBoardEntity);
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public PageImpl<LeaderBoardEntity> searchViewBoard(Pageable pageable, BoardSearchDTO searchDTO) {
        String likeKeyword = "%" + searchDTO.searchKeyword().toLowerCase() + "%";
        String searchOption = searchDTO.searchOption().toLowerCase();

        /*if ("all".equals(searchOption) || searchOption.isEmpty()) {
            booleanExpression = leaderBoardEntity.boardWriter.like(likeKeyword)
                    .or(leaderBoardEntity.boardTitle.like(likeKeyword))
                    .or(leaderBoardEntity.boardContents.like(likeKeyword));
        } else if ("content".equals(searchOption)) {
            booleanExpression = leaderBoardEntity.boardContents.like(likeKeyword);
        } else if ("writeruser".equals(searchDTO.searchOption())) {
            booleanExpression = leaderBoardEntity.boardWriter.like(likeKeyword);
        } else if ("title".equals(searchDTO.searchOption())) {
            booleanExpression = leaderBoardEntity.boardTitle.like(likeKeyword);
        }*/
        return dslRepeatCode.searchKeywordAndOption(leaderBoardEntity, booleanExpression, searchDTO.searchKeyword(), pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public Page<LeaderBoardEntity> filterViewBoard(Pageable pageable) {
        /*booleanExpression.eq(leaderBoardEntity.boardLikes.gt(200));*/
        return null;
    }

    /**{@inheritDoc}*/
    @Override
    public Page<LeaderBoardEntity> onlyViewFilterByWaring(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(leaderBoardEntity)
                .join(leaderBoardImpeachEntity)
                .on(leaderBoardImpeachEntity.id.eq(leaderBoardImpeachEntity.board.id))
                .where(leaderBoardEntity.boardUsing.notIn(BoardUsing.BLOCK)
                        .and(JPAExpressions
                                .select(leaderBoardImpeachEntity.board.id.count())
                                .from(leaderBoardImpeachEntity)
                                .where(leaderBoardEntity.id.eq(leaderBoardImpeachEntity.board.id))
                                .groupBy(leaderBoardEntity.id)
                                .having(leaderBoardEntity.id.count().goe(30))
                                .exists()
                        )
                )
                .distinct();
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public Page<LeaderBoardEntity> onlyViewFilterByBlock(Pageable pageable) {
        booleanExpression = leaderBoardEntity.boardUsing.in(BoardUsing.BLOCK);
        boardEntityJPAQuery = queryFactory.selectFrom(leaderBoardEntity)
                .where(booleanExpression)
                .orderBy(leaderBoardEntity.id.desc());
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }

    /**{@inheritDoc}*/
    @Override
    public Page<LeaderBoardEntity> findAll(Pageable pageable) {
        boardEntityJPAQuery = queryFactory.selectFrom(leaderBoardEntity)
                .where(leaderBoardEntity.boardUsing.notIn(BoardUsing.BLOCK))
                .orderBy(leaderBoardEntity.id.desc());
        return commonQueryDsl.pageableHandler(boardEntityJPAQuery, pageable);
    }
}
