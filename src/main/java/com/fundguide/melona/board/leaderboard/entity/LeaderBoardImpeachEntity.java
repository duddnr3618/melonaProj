package com.fundguide.melona.board.leaderboard.entity;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leaderBoardImpeach")
/*TODO 신고하는 버튼 및 처리 메서드 만들것*/
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
