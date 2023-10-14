package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.common.role.BoardUsing;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.lang.reflect.Field;
import java.util.Collections;


public class ImpeachJoinQuery {

    protected <T> EntityPath<T> findByClassEntityPath(Class<?> entityClass, String variableName) {
        return new EntityPathBase<T>((Class<? extends T>) entityClass, variableName);
    }

    public <T> JPAQuery<T> boardAndImpeachJoinQuery(
            JPAQueryFactory queryFactory,
            Class<T> boardClass,
            Class<T> impeachClass
    ) {

        EntityPath<T> board = findByClassEntityPath(boardClass, "board");
        EntityPath<T> impeach = findByClassEntityPath(impeachClass, "impeach");


        try {
            Field boardIdField = boardClass.getDeclaredField("id");
            Field boardUsingField = boardClass.getDeclaredField("using");
            Field impeachBoardIdField = impeachClass.getDeclaredField("board.id");

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
