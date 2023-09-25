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
            //  return memberEntity.memberEmail.like("%" + memberEmail + "%");
        }
        return null;
    }

    private BooleanExpression nickname(String memberNickname) {
        if (StringUtils.hasText(memberNickname)) {
            return memberEntity.memberNickname.eq(memberNickname);
            // return memberEntity.memberNickname.like("%" + memberNickname + "%");
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

    @Override
    @Transactional
    public void updatePassword(Long memberId, String newPassword) {
        MemberEntity memberEntity = em.find(MemberEntity.class, memberId);
        memberEntity.setMemberPassword(newPassword);
    }
}
