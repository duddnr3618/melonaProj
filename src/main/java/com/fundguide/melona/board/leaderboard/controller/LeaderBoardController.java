package com.fundguide.melona.board.leaderboard.controller;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/leaderBoard")
@RequiredArgsConstructor
public class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;

    //게시글 작성폼
    @GetMapping("/writeForm")
    public String writeForm () {

        return "board/leader/writeForm";
    }
    
    // 게시글 작성 처리
    @PostMapping("/writePro")
    public String writepro(@ModelAttribute LeaderBoardDto leaderBoardDto) {
        System.out.println(">>>>>>>>>> leaderBoardDto : " + leaderBoardDto );
        leaderBoardService.save(leaderBoardDto);
        return "index";
    }

    // 게시글 리스트
    @GetMapping("/list")
    public String findAll (Model model) {
        List<LeaderBoardDto> leaderBoardDtoList = leaderBoardService.findAll();
        model.addAttribute("leaderboardList" , leaderBoardDtoList);

        return "board/leader/list";
    }
    
    // 게시글 상세보기
    @GetMapping("/{id}")
    public String findById (@PathVariable Long id , Model model) {
        leaderBoardService.updateHits(id);
        LeaderBoardDto leaderBoardDto =  leaderBoardService.findById(id);
        model.addAttribute("leaderBoard" , leaderBoardDto);
        return "/board/leader/detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id ,  Model model) {
        LeaderBoardDto leaderBoardDto = leaderBoardService.findById(id);
        model.addAttribute("leaderBoardUpdate" , leaderBoardDto);
       return "board/leader/update";
    }



    // 게시글 수정 처리
    @PostMapping("/update")
    public String update (@ModelAttribute LeaderBoardDto leaderBoardDto , Model model) {
       LeaderBoardDto board =  leaderBoardService.update(leaderBoardDto);
       model.addAttribute("board" , board);
       return "board/leader/detail";
    }




}
