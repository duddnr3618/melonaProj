package com.fundguide.melona.board.community.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable @Builder @AllArgsConstructor @NoArgsConstructor
public class CommentLikeIdG implements Serializable {
    private Long userId;
    private Long boardId;
}
