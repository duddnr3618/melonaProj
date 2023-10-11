package com.fundguide.melona.board.community.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.*;

import java.io.Serializable;

@Embeddable @Builder @AllArgsConstructor @NoArgsConstructor
public class CommentLikeIdG implements Serializable {
    private Long userId;
    private Long boardId;
}
