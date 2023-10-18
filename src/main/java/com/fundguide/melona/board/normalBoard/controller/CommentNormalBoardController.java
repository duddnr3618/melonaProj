package com.fundguide.melona.board.normalBoard.controller;

import com.fundguide.melona.board.normalBoard.dto.CommentNormalBoardDto;
import com.fundguide.melona.board.normalBoard.service.CommentNormalBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/commentNormalBoard")
@RequiredArgsConstructor
public class CommentNormalBoardController {
    private final CommentNormalBoardService commentNormalBoardService;

    @PostMapping("/save")
    public ResponseEntity save (@ModelAttribute CommentNormalBoardDto commentNormalBoardDto) {
        System.out.println("commentNormalBoardDto 1: " + commentNormalBoardDto);
         Long saveResult =  commentNormalBoardService.save(commentNormalBoardDto);
        System.out.println(">>>>>>>saveResult 2: " + saveResult);
        if(saveResult != null){
             List<CommentNormalBoardDto> commentNormalBoardDtoList = commentNormalBoardService.findAll(commentNormalBoardDto.getBoardId());
            System.out.println(">>>>>>>>>>> 3 : commentNormalBoardDtoList" + commentNormalBoardDtoList);
            return new ResponseEntity<>(commentNormalBoardDtoList, HttpStatus.OK);
        }else  {
            System.out.println(">>>>>>>>>>> 4 : commentNormalBoardDtoList");
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }



    }
}
