package com.fundguide.melona.board.community.entity;

import com.fundguide.melona.board.common.entity.BaseBoardEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "community_board")
public class CommunityEntity extends BaseBoardEntity {
    @Id
    @Column(name = "community_board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardTitle;
    private String boardContents;
    private int boardHits;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;



}
