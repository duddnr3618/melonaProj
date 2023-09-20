package com.fundguide.melona.board.common.querydsl_repeatcode;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor @Configuration
public class BoardQueryDsl_RepeatCode {
    private final JPAQueryFactory queryFactory;

    /**keyword 존재할시 검색하는 메서드 null일 경우 전체값을 반환함
     * @return PageImpl*/
    public <T> PageImpl<T> searchKeywordAndOption(EntityPath<T> table, BooleanExpression booleanExpression, String searchKeyword, Pageable pageable) {
        JPAQuery<T> query = queryFactory.selectFrom(table);
        long total = query.fetch().size();
        List<T> page;
        if (booleanExpression != null && !searchKeyword.trim().isEmpty()) {
            total = query
                    .where(booleanExpression)
                    .fetch().size();

            page = query
                    .where(booleanExpression)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {
            page = query
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }
        return new PageImpl<>(page, pageable, total);
    }
}
