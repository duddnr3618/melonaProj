package com.fundguide.melona.board.leaderboard.repository;

import com.fundguide.melona.board.leaderboard.entity.CommentLeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLeaderBoardRepository extends JpaRepository<CommentLeaderBoardEntity, Long> {
    List<CommentLeaderBoardEntity> findAllByLeaderBoardEntityOrderByIdDesc(LeaderBoardEntity leaderBoardEntity);

}
