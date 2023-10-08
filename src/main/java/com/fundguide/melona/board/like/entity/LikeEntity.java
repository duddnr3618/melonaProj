package com.fundguide.melona.board.like.entity;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "board_like",
                        columnNames = {"community_board_id" , "member_id"}
                )
        })
public class LikeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long like;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_board_id")
    private CommunityEntity communityEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_board_id")
    private LeaderBoardEntity LeaderBoardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_board_id")
    private LeaderBoardEntity normalBoardEntity;
}
