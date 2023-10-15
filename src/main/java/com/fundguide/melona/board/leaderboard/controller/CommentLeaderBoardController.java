package com.fundguide.melona.board.leaderboard.controller;

import com.fundguide.melona.board.community.dto.CommentDto;
import com.fundguide.melona.board.community.service.CommentService;
import com.fundguide.melona.board.leaderboard.dto.CommentLeaderBoardDto;
import com.fundguide.melona.board.leaderboard.service.CommentLeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/commentLeaderBoard")
@RequiredArgsConstructor
public class CommentLeaderBoardController {
    private final CommentLeaderBoardService commentLeaderBoardService;

    @PostMapping("/save")
    public ResponseEntity save (@ModelAttribute CommentLeaderBoardDto commentLeaderBoardDto) {
        System.out.println("commentLeaderBoardDto : " + commentLeaderBoardDto);
         Long saveResult =  commentLeaderBoardService.save(commentLeaderBoardDto);
        System.out.println(">>>>>>>saveResult : " + saveResult);
        if(saveResult != null){
            List<CommentLeaderBoardDto> commentLeaderBoardDtoList = commentLeaderBoardService.findAll(commentLeaderBoardDto.getBoardId());
            System.out.println(">>>>>>>>>>> : commentLeaderBoardDtoList" + commentLeaderBoardDtoList);
            return new ResponseEntity<>(commentLeaderBoardDtoList, HttpStatus.OK);
        }else  {
            System.out.println(">>>>>>>>>>> : commentLeaderBoardDtoList");
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }



    }
}