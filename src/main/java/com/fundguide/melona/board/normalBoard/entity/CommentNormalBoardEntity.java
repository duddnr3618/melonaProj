package com.fundguide.melona.board.normalBoard.entity;

import com.fundguide.melona.board.leaderboard.dto.CommentLeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.CommentLeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.normalBoard.dto.CommentNormalBoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "comment_normal_board")
public class CommentNormalBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentWriter;

    @Column(nullable = false)
    private String commentContents;

    /* NormalBoard와 연관관계 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_board_id")
    private NormalBoardEntity normalBoardEntity;

    public static CommentNormalBoardEntity toSaveNormalBoardEntity(CommentNormalBoardDto commentNormalBoardDto, NormalBoardEntity normalBoardEntity) {
        CommentNormalBoardEntity commentNormalBoardEntity = new CommentNormalBoardEntity();
        commentNormalBoardEntity.setCommentWriter(commentNormalBoardDto.getCommentWriter());
        commentNormalBoardEntity.setCommentContents(commentNormalBoardDto.getCommentContents());
        commentNormalBoardEntity.setNormalBoardEntity(normalBoardEntity);
        return commentNormalBoardEntity;
    }

}
