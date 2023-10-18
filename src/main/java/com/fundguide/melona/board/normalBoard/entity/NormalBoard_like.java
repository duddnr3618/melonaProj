package com.fundguide.melona.board.normalBoard.entity;

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
@Table(name = "normal_board_like")
public class NormalBoard_like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "normal_board_board_id")
    private NormalBoardEntity normalBoardEntity;

    public static NormalBoard_like likeFastBuilder(NormalBoardEntity normalBoardEntity, MemberEntity memberEntity) {
        return NormalBoard_like.builder()
                .normalBoardEntity(normalBoardEntity)
                .memberEntity(memberEntity)
                .build();
    }
}