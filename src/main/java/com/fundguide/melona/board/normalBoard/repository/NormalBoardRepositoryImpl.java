package com.fundguide.melona.board.normalBoard.repository;


import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static com.fundguide.melona.board.normalBoard.entity.QNormalBoardEntity.normalBoardEntity;
@Repository @RequiredArgsConstructor
public class NormalBoardRepositoryImpl implements NormalBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final JPAQuery<NormalBoardEntity> boardEntityJPAQuery = null;
    private final BooleanExpression booleanExpression = null;
}
