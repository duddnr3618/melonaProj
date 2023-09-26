package com.fundguide.melona.member.repository;

import com.fundguide.melona.management.dto.MemberRoleFilterDTO;
import com.fundguide.melona.management.dto.QMemberRoleFilterDTO;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import static com.fundguide.melona.member.entity.QMemberEntity.memberEntity;

@Repository
@Slf4j
public class MemberRepositoryJpa implements MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;
    private BooleanExpression expression = null;


    public MemberRepositoryJpa(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Transactional
    @Override
    public void memberSave(MemberEntity memberEntity) {
        em.persist(memberEntity);
    }

    @Transactional(readOnly = true)
    public List<MemberEntity> findMember(String email, Integer age) {
        return query
                .select(memberEntity)
                .from(memberEntity)
                .where(memberName(email), nickname(age))
                .fetch();
    }


    private BooleanExpression memberName(String email) {
        if (StringUtils.hasText(email)) {
            return memberEntity.memberEmail.like("%" + email + "%");
        }
        return null;
    }

    private BooleanExpression nickname(Integer age) {
        if (age != null) {
            return memberEntity.id.loe(age);
        }
        return null;
    }

    @Override
    public MemberEntity findByEmail(String username) {
        TypedQuery<MemberEntity> query = em.createQuery(
                "SELECT m FROM MemberEntity m WHERE m.memberEmail=:email", MemberEntity.class);
        query.setParameter("email", username);
        List<MemberEntity> resultList = query.getResultList();
        return resultList.get(0);
    }

    /**memberRepositoryCustom -> this.class 병합*/
    @Override
    @Transactional(readOnly = true)
    public Page<MemberEntity> memberLimitStatePage(MemberLimitState state, Pageable pageable) {
        expression = memberEntity.memberLimitState.eq(state);

        JPAQuery<MemberEntity> jpaQuery = this.query.selectFrom(memberEntity)
                .where(expression);

        return getMemberEntities(jpaQuery, pageable);
    }

    @Override
    public Page<MemberEntity> memberRoleStatePage(Pageable pageable) {
        JPQLQuery<MemberRoleFilterDTO> subquery = JPAExpressions
                .select(new QMemberRoleFilterDTO(memberEntity.memberName, memberEntity.memberNickname, memberEntity.memberRole, memberEntity.memberLimitState))
                .from(memberEntity);


        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PageImpl<MemberRoleState> memberRoleStatePage(String filter, Pageable pageable) {
        switch (filter) {
            case "autoGet" -> {
                expression = memberEntity.memberRole.eq(MemberRoleState.ROLE_AUTO_LEADER);
            }
            case "setAdmin" -> {
                expression = memberEntity.memberRole.eq(MemberRoleState.ROLE_SET_LEADER);
            }
            default -> throw new IllegalArgumentException("정의되지 않은 필터 값입니다.");
        }

        JPAQuery<MemberRoleState> jpaQuery = this.query
                .select(memberEntity.memberRole)
                .from(memberEntity)
                .where(expression);

        return getMemberEntities(jpaQuery, pageable);
    }

    private <Member> PageImpl<Member> getMemberEntities(JPAQuery<Member> jpaQuery ,Pageable pageable) {
        long total = jpaQuery.fetch().size();
        List<Member> memberEntities = jpaQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(memberEntities, pageable, total);
    }
}
