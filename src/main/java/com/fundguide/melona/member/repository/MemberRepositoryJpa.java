package com.fundguide.melona.member.repository;

import com.fundguide.melona.management.dto.MemberRoleFilterDTO;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.querydsl.core.types.dsl.BooleanExpression;
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

import java.util.Date;
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
    public MemberEntity findMember(String memberEmail , String memberNickname){
        List<MemberEntity> result = query
                .select(memberEntity)
                .from(memberEntity)
                .where(memberName(memberEmail),nickname(memberNickname))
                .fetch();

        if (result.isEmpty()) {
            MemberEntity memberEntity1 =null;
            result.add(memberEntity1);
        }


    return result.get(0);
    }


    private BooleanExpression memberName(String memberEmail) {
        if (StringUtils.hasText(memberEmail)) {
            return memberEntity.memberEmail.eq(memberEmail);
        }
        return null;
    }

    private BooleanExpression nickname(String memberNickname) {
        if (StringUtils.hasText(memberNickname)) {
            return memberEntity.memberNickname.eq(memberNickname);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberEntity findByEmail(String username) {
        TypedQuery<MemberEntity> query = em.createQuery(
                "SELECT m FROM MemberEntity m WHERE m.memberEmail=:email", MemberEntity.class);
        query.setParameter("email", username);
        List<MemberEntity> resultList = query.getResultList();
        return resultList.get(0);
    }

    /** memberRepositoryCustom -> this.class 병합 */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberEntity> memberLimitStatePage(MemberLimitState state, Pageable pageable) {
        expression = memberEntity.memberLimitState.eq(state);

        JPAQuery<MemberEntity> jpaQuery = this.query
                .selectFrom(memberEntity)
                .where(expression);
        return getMemberEntities(jpaQuery, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberRoleFilterDTO> memberRoleStatePage(Pageable pageable) {
        JPAQuery<MemberRoleFilterDTO> jpaQuery = query
                .select(MemberRoleFilterDTO.projections())
                .from(memberEntity);
        return getMemberEntities(jpaQuery, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberRoleFilterDTO> memberRoleStatePage(String filter, Pageable pageable) {
        switch (filter) {
            case "minSatisfy" -> {

            }
            case "autoGet" -> {
                expression = memberEntity.memberRole.eq(MemberRoleState.ROLE_AUTO_LEADER);
            }
            case "setByAdmin" -> {
                expression = memberEntity.memberRole.eq(MemberRoleState.ROLE_SET_LEADER);
            }
            default -> throw new IllegalArgumentException("정의되지 않은 필터 값입니다.");
        }

        JPAQuery<MemberRoleFilterDTO> jpaQuery = this.query
                .select(MemberRoleFilterDTO.projections())
                .from(memberEntity)
                .where(expression);
        return getMemberEntities(jpaQuery, pageable);
    }

    private <Member> PageImpl<Member> getMemberEntities(JPAQuery<Member> jpaQuery, Pageable pageable) {
        long total = jpaQuery.fetch().size();
        List<Member> memberEntities = jpaQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(memberEntities, pageable, total);
    }
}
