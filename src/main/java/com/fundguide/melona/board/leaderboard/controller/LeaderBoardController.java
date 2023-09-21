package com.fundguide.melona.board.leaderboard.controller;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LeaderBoardController {
    private final LeaderBoardCommandService leaderBoardCommandService;
    @GetMapping("/board/writeForm")
    public String writeForm () {

        return "board/writeForm";
    }

    @PostMapping("/board/writePro")
    public String writepro(@ModelAttribute LeaderBoardDto leaderBoardDto) {
        System.out.println(">>>>>>>>>> leaderBoardDto : " + leaderBoardDto );
        leaderBoardCommandService.save(leaderBoardDto);
        return "index";
    }

}
