package com.fundguide.melona.board.leaderboard.entity;

import com.fundguide.melona.board.community.entity.CommunityEntity;
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
@Table(name = "leader_board_like")
public class LeaderBoard_like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "leader_board_id")
    private LeaderBoardEntity leaderBoardEntity;

    private int boardCount;



    public static LeaderBoard_like likeFastBuilder(LeaderBoardEntity leaderBoardEntity, MemberEntity memberEntity) {
        return LeaderBoard_like.builder()
                .leaderBoardEntity(leaderBoardEntity)
                .memberEntity(memberEntity)
                .boardCount(leaderBoardEntity.getBoardLike().size())
                .build();
    }
}