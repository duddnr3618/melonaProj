package com.fundguide.melona.member.repository;

import com.fundguide.melona.board.normalBoard.entity.QNormalBoardEntity;
import com.fundguide.melona.board.normalBoard.entity.QNormalBoardImpeachEntity;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.fundguide.melona.member.dto.MemberLeastDTO;
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
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
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

    @Override
    @Transactional
    public void updatePassword(Long memberId, String newPassword) {
        MemberEntity memberEntity = em.find(MemberEntity.class, memberId);
        memberEntity.setMemberPassword(newPassword);
    }

    @Override
    @Transactional
    public void memberUpdate(MemberEntity memberEntity) {
        MemberEntity memberEntity1 = em.find(MemberEntity.class, memberEntity.getId());
        memberEntity1.setMemberNickname(memberEntity.getMemberNickname());
        memberEntity1.setMemberName(memberEntity.getMemberName());
        memberEntity1.setMemberAddress(memberEntity.getMemberAddress());
    }

    @Override
    @Transactional
    public void withdraw(Long id) {
        MemberEntity memberEntity = em.find(MemberEntity.class, id);
        memberEntity.setMemberAvailable("no");
        memberEntity.setMemberEmail("탈퇴한사용자"+ memberEntity.getId());
        memberEntity.setMemberNickname("탈퇴한사용자"+memberEntity.getId()+new Date());
        memberEntity.setMemberRole(MemberRoleState.DISABLED);
        memberEntity.setMemberAddress("탈퇴한사용자");
        memberEntity.setMemberName("탈퇴한사용자");

    }

    /** memberRepositoryCustom -> this.class 병합 */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberLeastDTO> memberLimitStatePage(MemberLimitState state, Pageable pageable) {
        expression = memberEntity.memberLimitState.eq(state);

        JPAQuery<MemberLeastDTO> jpaQuery = this.query
                .select(MemberLeastDTO.projections())
                .from(memberEntity)
                .where(expression);
        return commonQueryDsl.pageableHandler(jpaQuery, pageable);
    }

    /**필요값을 DTO로 변환 시켜 출력하는 메서드 readOnly
     * @param pageable 페이저블 처리를 위한 값
     * @return Page-All*/
    @Override
    @Transactional(readOnly = true)
    public Page<MemberLeastDTO> findAllOfMemberLeastData(Pageable pageable) {
        JPAQuery<MemberLeastDTO> jpaQuery = query
                .select(MemberLeastDTO.projections())
                .from(memberEntity);
        return commonQueryDsl.pageableHandler(jpaQuery, pageable);
    }

    /** 필터링된 멤버 Role DTO로 전달해 페이징 처리하는 메서드 readOnly
     * 더 간략화 시킬 수 있을거 같은데...
     * @param filter 필터처리를 위한 값 필요시 case를 더 설정할것
     * @param pageable 페이저블 처리를 위한 값
     * @return Page*/
    @Override
    @Transactional(readOnly = true)
    public Page<MemberLeastDTO> memberRoleStateFilterPage(String filter, Pageable pageable) {
        switch (filter) {
            case "minSatisfy" -> expression = memberEntity.memberRole.eq(MemberRoleState.ROLE_USER);
            case "autoGet" -> expression = memberEntity.memberRole.eq(MemberRoleState.ROLE_AUTO_LEADER);
            case "setByAdmin" -> expression = memberEntity.memberRole.eq(MemberRoleState.ROLE_SET_LEADER);
            default -> throw new IllegalArgumentException("정의되지 않은 필터 값입니다.");
        }

        JPAQuery<MemberLeastDTO> jpaQuery = this.query
                .select(MemberLeastDTO.projections())
                .from(memberEntity)
                .where(expression);
        return commonQueryDsl.pageableHandler(jpaQuery, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberLeastDTO> evaluatePendingByRule(String filter, Pageable pageable) {
        QNormalBoardImpeachEntity qNormalBoardImpeach = QNormalBoardImpeachEntity.normalBoardImpeachEntity;
        /*expression = memberEntity.id.eq(qNormalBoardImpeach.board.boardWriter);*/
        return null;
    }
}
