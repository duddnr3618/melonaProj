package com.fundguide.melona.board.common.role;

import java.util.NoSuchElementException;

public enum BoardUsing {
    USING, BLOCK;

    public static BoardUsing getBoardUsing(String type) throws NoSuchElementException {
        for (BoardUsing boardUsing : BoardUsing.values()) {
            if (boardUsing.toString().equals(type)) {
                return boardUsing;
            }
        }
        throw new NoSuchElementException("게시판 상태값 존재하지 않음");
    }
}
