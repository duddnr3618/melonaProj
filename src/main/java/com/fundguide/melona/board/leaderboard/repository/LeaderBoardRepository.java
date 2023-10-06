package com.fundguide.melona.board.leaderboard.repository;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaderBoardRepository extends JpaRepository<LeaderBoardEntity , Long> {
    @Modifying
    @Query(value = "update LeaderBoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    @Modifying
    @Query("UPDATE LeaderBoardEntity b SET b.updatedTime = CURRENT_TIMESTAMP WHERE b.id = :id")
    void updateBoardUpdateTime(@Param("id") Long id);

//    @Query("SELECT b FROM LeaderBoardEntity b WHERE b.memberEntity.id = :memberId ORDER BY b.boardHits DESC")
//    List<LeaderBoardEntity> findMyPagePostsOrderByHits(@Param("memberId") Long memberId);
//
//    @Query("SELECT b FROM LeaderBoardEntity b WHERE b.memberEntity.id = :memberId ORDER BY b.createdTime DESC")
//    List<LeaderBoardEntity> findMyPagePostsByMemberIdOrderByCreatedAt(@Param("memberId") Long memberId);


}
