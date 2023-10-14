package com.fundguide.melona.board.normalBoard.dto;

import com.fundguide.melona.board.normalBoard.entity.CommentNormalBoardEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class CommentNormalBoardDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentNormalBoardDto toCommentNormalBoardDto(CommentNormalBoardEntity commentNormalBoardEntity) {
        CommentNormalBoardDto commentNormalBoardDto = new CommentNormalBoardDto();
        commentNormalBoardDto.setId(commentNormalBoardEntity.getId());
        commentNormalBoardDto.setCommentWriter(commentNormalBoardEntity.getCommentWriter());
        commentNormalBoardDto.setCommentContents(commentNormalBoardEntity.getCommentContents());
        commentNormalBoardDto.setCommentCreatedTime(commentNormalBoardEntity.getNormalBoardEntity().getCreatedTime());
        commentNormalBoardDto.setBoardId(commentNormalBoardEntity.getId());
        return commentNormalBoardDto;
    }

}
