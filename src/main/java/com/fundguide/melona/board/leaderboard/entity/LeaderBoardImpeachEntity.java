package com.fundguide.melona.board.leaderboard.entity;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leader_board_impeach")
public class LeaderBoardImpeachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private LeaderBoardEntity board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column
    private String cause;
}
