package com.fundguide.melona.board.community.controller;

import ch.qos.logback.core.CoreConstants;
import com.fundguide.melona.board.community.dto.CommentDto;
import com.fundguide.melona.board.community.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save (@ModelAttribute CommentDto commentDto) {
        System.out.println("commentDto : " + commentDto);
         Long saveResult =  commentService.save(commentDto);
        System.out.println(">>>>>>>saveResult : " + saveResult);
        if(saveResult != null){
            List<CommentDto> commentDtoList = commentService.findAll(commentDto.getBoardId());
            System.out.println(">>>>>>>>>>> : commentDtoList" + commentDtoList);
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
        }else  {
            System.out.println(">>>>>>>>>>> : commentDtoList");
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }



    }
}
