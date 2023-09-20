package com.fundguide.melona.member.repository;

import com.fundguide.melona.member.entity.MemberEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.fundguide.melona.member.entity.QMemberEntity.memberEntity;

@Repository
@Slf4j
public class MemberRepositoryJpa implements MemberRepository {

    private final EntityManager em;
    private  final JPAQueryFactory query;


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
    public List<MemberEntity> findMember(String email , Integer age){
        List<MemberEntity> result = query
                .select(memberEntity)
                .from(memberEntity)
                .where(memberName(email),nickname(age))
                .fetch();
    return result;
    }


    private BooleanExpression memberName(String email) {
        if (StringUtils.hasText(email)) {
            return memberEntity.memberEmail.like("%" + email + "%");
        }
        return null;
    }
    private BooleanExpression nickname(Integer age) {
        if(age != null){
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
}
