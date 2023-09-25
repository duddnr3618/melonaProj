package com.fundguide.melona.member.repository;


import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fundguide.melona.member.entity.QMemberEntity.memberEntity;

@SuppressWarnings("FieldCanBeLocal")
@Repository @RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    private BooleanExpression expression = null;

    @Override
    public Page<MemberEntity> memberLimitStatePage(MemberLimitState state, Pageable pageable) {
        expression = memberEntity.memberLimitState.eq(state);

        JPAQuery<MemberEntity> query = queryFactory.selectFrom(memberEntity);
        long total = query.fetch().size();

        List<MemberEntity> memberList = query
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(memberList, pageable, total);
    }
}
