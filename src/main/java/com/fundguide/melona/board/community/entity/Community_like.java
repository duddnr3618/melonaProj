package com.fundguide.melona.board.community.entity;

import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "community_like")
public class Community_like {

    @EmbeddedId
    private CommentLikeIdG id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "community_board_id")
    private CommunityEntity communityEntity;

    public static CommentLikeIdG idG(MemberEntity memberEntity, CommunityEntity communityEntity) {
        return CommentLikeIdG.builder()
                .boardId(communityEntity.getId())
                .userId(memberEntity.getId())
                .build();
    }
}