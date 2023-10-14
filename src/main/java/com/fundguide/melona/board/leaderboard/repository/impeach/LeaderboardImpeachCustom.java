package com.fundguide.melona.board.leaderboard.repository.impeach;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardImpeachEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardImpeachEntity;

public interface LeaderboardImpeachCustom {
    
    /**해당 게시물에 이미 신고했는지 확인*/
    boolean checkAlreadyImpeach(LeaderBoardImpeachEntity impeach);
}
