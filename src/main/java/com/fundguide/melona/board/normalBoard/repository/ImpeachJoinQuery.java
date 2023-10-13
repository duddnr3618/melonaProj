package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.entity.QCommunityEntity;
import com.fundguide.melona.board.community.entity.QCommunityImpeachEntity;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.lang.reflect.Field;
import java.util.Collections;


public class ImpeachJoinQuery {
    public static <T> JPAQuery<T> joinQuery(
            JPAQueryFactory queryFactory,
            EntityPath<T> board,
            EntityPath<T> impeach
    ) {

        /*boardEntityJPAQuery = queryFactory.selectFrom(communityEntity)
                .where(communityEntity.boardUsing.notIn(BoardUsing.BLOCK))
                .join(communityImpeachEntity)
                .on(communityEntity.id.eq(communityImpeachEntity.board.id))
                .orderBy(communityEntity.id.desc())
                .distinct();
                */

        Class<?> boardMetaModel = board.getClass();
        Class<?> impeachMetaModel = impeach.getClass();

        try {
            Field boardIdField = boardMetaModel.getDeclaredField("id");
            Field boardUsingField = boardMetaModel.getDeclaredField("using");
            Field impeachBoardIdField = impeachMetaModel.getDeclaredField("board.id");

            Expression<Long> boardId = (Expression<Long>) boardIdField.get(board);
            Expression<BoardUsing> boardUsing = (Expression<BoardUsing>) boardUsingField.get(board);
            Expression<Long> impeachBoardId = (Expression<Long>) impeachBoardIdField.get(impeach);

            Predicate onCondition = Expressions.booleanOperation(Ops.EQ, boardId, impeachBoardId);
            Predicate boardUsingNotInBlock = Expressions.booleanOperation(Ops.NOT_IN, boardUsing, (Expression<?>) Collections.singletonList(BoardUsing.BLOCK));
            OrderSpecifier<?> orderByIdDesc = new OrderSpecifier<>(Order.DESC, boardId);

            return queryFactory.selectFrom(board)
                    .where(boardUsingNotInBlock)
                    .join(impeach)
                    .on(onCondition)
                    .orderBy(orderByIdDesc)
                    .distinct();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
