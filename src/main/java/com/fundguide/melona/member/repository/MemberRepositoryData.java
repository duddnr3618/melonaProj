package com.fundguide.melona.member.repository;

import com.fundguide.melona.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryData extends JpaRepository<MemberEntity,Long> {

    MemberEntity findByMemberEmail(String email);
}
