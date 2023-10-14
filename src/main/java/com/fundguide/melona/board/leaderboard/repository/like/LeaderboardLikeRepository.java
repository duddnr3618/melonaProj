package com.fundguide.melona.board.leaderboard.repository.like;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoard_like;
import com.fundguide.melona.board.normalBoard.entity.NormalBoard_like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardLikeRepository extends JpaRepository<LeaderBoard_like, Long>, LeaderboardLikeCustom {

}
