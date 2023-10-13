package com.fundguide.melona.board.community.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.community.dto.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class CommentEntity extends BaseTimeEntity {
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
    private CommunityEntity communityEntity;

    public static CommentEntity toSaveEntity(CommentDto commentdDto, CommunityEntity communityEntity) {
        CommentEntity commentdEntity = new CommentEntity();
        commentdEntity.setCommentWriter(commentdDto.getCommentWriter());
        commentdEntity.setCommentContents(commentdDto.getCommentContents());
        commentdEntity.setCommunityEntity(communityEntity);
        return commentdEntity;
    }
}
