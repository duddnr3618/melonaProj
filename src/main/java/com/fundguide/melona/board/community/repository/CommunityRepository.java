package com.fundguide.melona.board.community.repository;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity,Long> {
    @Query("SELECT c FROM CommunityEntity c WHERE c.memberEntity.id = :memberId ORDER BY c.createdTime DESC")
    List<CommunityEntity> findMyPagePostsByMemberIdOrderByCreatedAt(@Param("memberId") Long memberId);

    @Query("SELECT c FROM CommunityEntity c JOIN c.likes l WHERE l.memberEntity.id = :memberId ORDER BY l.id DESC")
    List<CommunityEntity> findMyPagePostsByMemberIdOrderByLikes(@Param("memberId") Long memberId);
}

