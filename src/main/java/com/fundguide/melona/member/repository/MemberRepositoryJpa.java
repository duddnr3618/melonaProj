package com.fundguide.melona.member.repository;

import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.fundguide.melona.member.dto.MemberLeastDTO;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.MySQLTemplates;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fundguide.melona.board.normalBoard.entity.QNormalBoardEntity.normalBoardEntity;
import static com.fundguide.melona.board.normalBoard.entity.QNormalBoard_like.normalBoard_like;
import static com.fundguide.melona.member.entity.QMemberEntity.memberEntity;

@Repository
@Slf4j
public class MemberRepositoryJpa implements MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression expression = null;
    private JPAQuery<MemberEntity> jpaQuery = new JPAQuery<>();
    private final JPAQuery<MemberLeastDTO> leastJPAQuery = new JPAQuery<>();
    private final JPASQLQuery<MemberEntity> jpasqlQuery;


    public MemberRepositoryJpa(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
        this.jpasqlQuery = new JPASQLQuery<>(em, new MySQLTemplates());
    }

    @Transactional
    @Override
    public void memberSave(MemberEntity memberEntity) {
        em.persist(memberEntity);
    }

    @Transactional(readOnly = true)
    public MemberEntity findMember(String memberEmail, String memberNickname) {
        List<MemberEntity> result = query
                .select(memberEntity)
                .from(memberEntity)
                .where(memberName(memberEmail), nickname(memberNickname))
                .fetch();

        if (result.isEmpty()) {
            MemberEntity memberEntity1 = null;
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
        memberEntity.setMemberEmail("탈퇴한사용자" + memberEntity.getId());
        memberEntity.setMemberNickname("탈퇴한사용자" + memberEntity.getId() + new Date());
        memberEntity.setMemberEmail("탈퇴한사용자" + memberEntity.getId());
        memberEntity.setMemberNickname("탈퇴한사용자" + memberEntity.getId() + new Date());
        memberEntity.setMemberRole(MemberRoleState.DISABLED);
        memberEntity.setMemberAddress("탈퇴한사용자");
        memberEntity.setMemberName("탈퇴한사용자");
        memberEntity.setMemberLimitState(MemberLimitState.PERMANENT);
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

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberLeastDTO> findAllOfMemberLeastData(Pageable pageable) {
        JPAQuery<MemberLeastDTO> jpaQuery = query
                .select(MemberLeastDTO.projections())
                .from(memberEntity);
        return commonQueryDsl.pageableHandler(jpaQuery, pageable);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberEntity> getMemberAuthorityByRule(String filter, Pageable pageable) {

        switch (filter) {
            case "minSatisfy" -> jpaQuery = query.selectFrom(memberEntity)
                    .where(memberEntity.memberLimitState.notIn(MemberLimitState.PERMANENT)
                            .and(memberEntity.memberRole.in(MemberRoleState.ROLE_USER)))
                    .rightJoin(memberEntity)
                    .leftJoin(normalBoardEntity)
                    .on(memberEntity.id.eq(normalBoardEntity.memberEntity.id))
                    .leftJoin(normalBoard_like)
                    .on(normalBoardEntity.id.eq(normalBoard_like.normalBoardEntity.id))
                    .groupBy(memberEntity.id)
                    .orderBy(memberEntity.id.desc())
                    .having(normalBoard_like.count().goe(30))
                    .distinct();

            case "autoGet" ->
                    jpaQuery = query.selectFrom(memberEntity).where(memberEntity.memberRole.eq(MemberRoleState.ROLE_AUTO_LEADER))
                            .orderBy(memberEntity.id.desc());
            case "setByAdmin" ->
                    jpaQuery = query.selectFrom(memberEntity).where(memberEntity.memberRole.eq(MemberRoleState.ROLE_SET_LEADER))
                            .orderBy(memberEntity.id.desc());
            default -> throw new IllegalArgumentException("정의되지 않은 필터 값입니다.");
        }

        return commonQueryDsl.pageableHandler(jpaQuery, pageable);
    }

    @Override
    @Transactional
    public void adminSave(MemberEntity memberEntity) {
        em.persist(memberEntity);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberEntity> evaluatePendingByRule(String filter, Pageable pageable) {

        int countSize = 0;
        String subQueryAfterQuery;
        switch (filter) {
            case "TRANSITORY" -> {
                //가벼운 경고
                subQueryAfterQuery = "WHERE subquery.member_id NOT IN (" +
                        "SELECT member_id FROM member m " +
                        "WHERE m.member_limit_state IN ('PERMANENT', 'STRONG', 'TRANSITORY') " +
                        "OR m.member_role IN ('ROLE_OAUTH2', 'ROLE_ADMIN', 'DISABLED') " +
                        ") " +

                        "GROUP BY member_id HAVING COUNT(member_id) >= ?1 " +
                        "ORDER BY member_id DESC";
                countSize = 15;
            }
            case "STRONG" -> {
                //강력한 경고
                subQueryAfterQuery = "WHERE subquery.member_id NOT IN (" +
                        "SELECT member_id FROM member m " +
                        "WHERE m.member_limit_state IN ('STRONG', 'TRANSITORY') " +
                        "OR m.member_role IN ('ROLE_OAUTH2', 'ROLE_ADMIN', 'DISABLED') " +
                        ") " +

                        "GROUP BY member_id HAVING COUNT(member_id) >= ?1 " +
                        "ORDER BY member_id DESC";
                countSize = 25;
            }
            case "PERMANENT" -> {
                jpaQuery = query.selectFrom(memberEntity)
                        .where(memberEntity.memberLimitState.eq(MemberLimitState.PERMANENT));
                return commonQueryDsl.pageableHandler(jpaQuery, pageable);
            }
            default -> throw new IllegalArgumentException("정의되지 않은 필터 방식입니다.");
        }

        String intersectionSql = "SELECT member_id FROM (" +
                "SELECT ci.member_id FROM community_impeach ci " +
                "JOIN community_board cb ON ci.board_id = cb.community_board_id " +
                "GROUP BY ci.member_id HAVING COUNT(*) >= 30 " +
                "UNION ALL " +

                "SELECT li.member_id FROM leader_board_impeach li " +
                "JOIN leader_board lb ON li.board_id = lb.leader_board_id " +
                "GROUP BY li.member_id HAVING COUNT(*) >= 30 " +
                "UNION ALL " +

                "SELECT ni.member_id FROM normal_board_impeach ni " +
                "JOIN normal_board nb ON ni.board_id = nb.normal_board_id " +

                ") AS subquery " +
                subQueryAfterQuery;

        Query nativeIntersectionQuery = em.createNativeQuery(intersectionSql);
        nativeIntersectionQuery.setParameter(1, countSize);
        List<Long> subMemberId = nativeIntersectionQuery.getResultList();

        Query nativeMembersQuery;
        List<MemberEntity> entities;
        if (!subMemberId.isEmpty()) {
            String membersSql =
                    String.format("SELECT m.* FROM member m WHERE m.member_id IN(%s) " +
                                    "ORDER BY m.member_id DESC " +
                                    "LIMIT :limit OFFSET :offset ",
                            subMemberId.stream().map(String::valueOf).collect(Collectors.joining(",")));
            nativeMembersQuery = em.createNativeQuery(membersSql, MemberEntity.class);
            nativeMembersQuery.setParameter("limit", pageable.getPageSize());
            nativeMembersQuery.setParameter("offset", pageable.getOffset());
            entities = nativeMembersQuery.getResultList();
        } else {
            entities = new ArrayList<>();
        }

        long total = nativeIntersectionQuery.getResultList().size();
        return new PageImpl<>(entities, pageable, total);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MemberEntity> findByMemberEamilOptional(String email) {
        MemberEntity member = query.selectFrom(memberEntity)
                .where(memberEntity.memberEmail.eq(email))
                .fetchOne();
        return Optional.ofNullable(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberEntity> findAll(Pageable pageable) {
        JPAQuery<MemberEntity> memberEntityJPAQuery = query.selectFrom(memberEntity)
                .where(memberEntity.memberLimitState.notIn(MemberLimitState.PERMANENT)
                        .and(memberEntity.memberRole.notIn(MemberRoleState.DISABLED, MemberRoleState.ROLE_ADMIN)));
        return commonQueryDsl.pageableHandler(memberEntityJPAQuery, pageable);
    }

    @Override
    @Transactional
    public void oauthSave(MemberEntity memberEntity) {
        MemberEntity memberEntity1 = em.find(MemberEntity.class, memberEntity.getId());
        memberEntity1.setMemberLimitState(memberEntity.getMemberLimitState());
        memberEntity1.setMemberNickname(memberEntity.getMemberNickname());
        memberEntity1.setMemberAddress(memberEntity.getMemberAddress());
        memberEntity1.setMemberName(memberEntity.getMemberName());
        memberEntity1.setMemberPassword(memberEntity.getMemberPassword());
        memberEntity1.setMemberRole(memberEntity.getMemberRole());
    }
}
