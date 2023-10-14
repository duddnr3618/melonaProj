package com.fundguide.melona.board.leaderboard.dto;

import com.fundguide.melona.board.leaderboard.entity.CommentLeaderBoardEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class CommentLeaderBoardDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentLeaderBoardDto toCommentLeaderBoardDto(CommentLeaderBoardEntity commentLeaderBoardEntity) {
        CommentLeaderBoardDto commentLeaderBoardDto = new CommentLeaderBoardDto();
        commentLeaderBoardDto.setId(commentLeaderBoardEntity.getId());
        commentLeaderBoardDto.setCommentWriter(commentLeaderBoardEntity.getCommentWriter());
        commentLeaderBoardDto.setCommentContents(commentLeaderBoardEntity.getCommentContents());
        commentLeaderBoardDto.setCommentCreatedTime(commentLeaderBoardEntity.getCreatedTime());
        commentLeaderBoardDto.setBoardId(commentLeaderBoardEntity.getId());
        return commentLeaderBoardDto;
    }

}
