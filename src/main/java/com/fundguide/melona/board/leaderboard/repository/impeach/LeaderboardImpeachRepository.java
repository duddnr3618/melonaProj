package com.fundguide.melona.board.leaderboard.repository.impeach;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardImpeachEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardImpeachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardImpeachRepository extends JpaRepository<LeaderBoardImpeachEntity, Long>, LeaderboardImpeachCustom {
}
