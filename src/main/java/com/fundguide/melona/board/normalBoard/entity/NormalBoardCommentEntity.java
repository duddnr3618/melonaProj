package com.fundguide.melona.board.normalBoard.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.community.dto.CommentDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "normalBoard_comment")
public class NormalBoardCommentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentWriter;

    @Column(nullable = false)
    private String commentContents;

    /* Community와 연관관계 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private NormalBoardEntity normalBoardEntity;

    public static NormalBoardCommentEntity toSaveEntity(CommentDto commentDto, NormalBoardEntity normalBoardEntity) {
        NormalBoardCommentEntity commentEntity = new NormalBoardCommentEntity();
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContents(commentDto.getCommentContents());
        commentEntity.setNormalBoardEntity(normalBoardEntity);
        return commentEntity;
    }
}
