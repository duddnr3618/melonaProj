package com.fundguide.melona.board.like.repository;

import com.fundguide.melona.board.like.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    // 좋아요가 가장 많은 게시글 ID 목록 가져오기
    @Query("SELECT l.communityEntity.id " +
            "FROM LikeEntity l " +
            "GROUP BY l.communityEntity.id " +
            "ORDER BY COUNT(l.communityEntity.id) DESC")
    List<Long> findMostLikedCommunityIds();
}
