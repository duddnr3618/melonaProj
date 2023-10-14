package com.fundguide.melona.board.normalBoard.repository.impeach;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardImpeachEntity;

public interface NormalBoardImpeachCustom {
    
    /**해당 게시물에 이미 신고했는지 확인*/
    boolean checkAlreadyImpeach(NormalBoardImpeachEntity impeach);
}
