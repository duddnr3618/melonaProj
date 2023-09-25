package com.fundguide.melona.board.leaderboard.repository;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderBoardRepository extends JpaRepository<LeaderBoardEntity, Long> {
}
