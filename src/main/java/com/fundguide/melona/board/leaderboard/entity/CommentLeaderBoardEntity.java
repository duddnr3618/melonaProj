package com.fundguide.melona.board.leaderboard.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.leaderboard.dto.CommentLeaderBoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_leader_board")
public class CommentLeaderBoardEntity extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentWriter;

    @Column(nullable = false)
    private String commentContents;

    /* LeaderBoard와 연관관계 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_board_id")
    private LeaderBoardEntity leaderBoardEntity;

    public static CommentLeaderBoardEntity toSaveLeaderBoardEntity(CommentLeaderBoardDto commentLeaderBoardDto, LeaderBoardEntity leaderBoardEntity) {
        CommentLeaderBoardEntity commentLeaderBoardEntity = new CommentLeaderBoardEntity();
        commentLeaderBoardEntity.setCommentWriter(commentLeaderBoardDto.getCommentWriter());
        commentLeaderBoardEntity.setCommentContents(commentLeaderBoardDto.getCommentContents());
        commentLeaderBoardEntity.setLeaderBoardEntity(leaderBoardEntity);
        return commentLeaderBoardEntity;
    }
}
