package com.fundguide.melona.board.leaderboard.repository;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeaderBoardRepository extends JpaRepository<LeaderBoardEntity , Long> {
    @Modifying
    @Query(value = "update LeaderBoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    @Modifying
    @Query(value = "update LeaderBoardEntity b set b.updatedTime = current_timestamp where b.id = :id")
    void updateTime(@Param("id") Long id);


}
