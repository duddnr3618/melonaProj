package com.fundguide.melona.board.leaderboard.controller;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.config.ResourceReaderRepositoryPopulatorBeanDefinitionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/leader")
@RequiredArgsConstructor
public class LeaderBoardController {
    private final LeaderBoardCommandService leaderBoardCommandService;

    @GetMapping("/writeForm")
    public String writeForm () {

        return "board/writeForm";
    }

    @PostMapping("/writePro")
    public String writepro(@ModelAttribute LeaderBoardDto leaderBoardDto) {
        System.out.println(">>>>>>>>>> leaderBoardDto : " + leaderBoardDto );
        leaderBoardCommandService.save(leaderBoardDto);
        return "index";
    }

    @GetMapping
    public String leaderList() {
        return null;
    }

    @PostMapping("good/{boardId}")
    public String good(Principal principal,
                       @PathVariable("boardId") Long boardId) {

        return "redirect:leader";
    }

}
