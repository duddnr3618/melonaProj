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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "community_board_id")
    private CommunityEntity communityEntity;

    private int boardCount;



    public static Community_like likeFastBuilder(CommunityEntity communityEntity, MemberEntity memberEntity) {
        return Community_like.builder()
                .communityEntity(communityEntity)
                .memberEntity(memberEntity)
                .boardCount(communityEntity.getBoardLike().size())
                .build();
    }
}