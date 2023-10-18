package com.fundguide.melona.management.commonQueryDsl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class CommonQueryDsl {
    public <T> Page<T> pageableHandler(JPAQuery<T> jpaQuery, Pageable pageable) {
        long total = jpaQuery.fetch().size();
        List<T> entities = jpaQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (entities.isEmpty()) {
            entities = new ArrayList<>();
        }
        return new PageImpl<>(entities, pageable, total);
    }
}
