package com.fundguide.melona.Member;

import com.fundguide.melona.Member.QueryDsl.MemberRepositoryCustom;
import com.fundguide.melona.Member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {
}
