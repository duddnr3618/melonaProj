package com.fundguide.melona.mypage.Controller;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardService;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.service.NormalBoardService;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.service.CustomUserDetails;
import com.fundguide.melona.mypage.Service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage")
public class MypageController {
    private final NormalBoardService normalBoardQueryService;
    private final CommunityService communityService;
    private final LeaderBoardService leaderBoardService;
    private final MypageService mypageService;

    @Autowired
    public MypageController(NormalBoardService normalBoardQueryService, CommunityService communityService, LeaderBoardService leaderBoardService, MypageService mypageService) {
        this.normalBoardQueryService = normalBoardQueryService;
        this.communityService = communityService;
        this.leaderBoardService = leaderBoardService;
        this.mypageService = mypageService;
    }

    @GetMapping("/mywrittenboard")
    public String viewMyWrittenBoard(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/member/loginForm";
        }
        return "mypage/mywrittenboard";
    }

    @GetMapping("/mypage2")
    public String viewMyPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0") int page) {
        if (userDetails == null) {
            return "redirect:/member/loginForm";
        }

        int pageSize = 10;  // 한 페이지에 보여줄 게시물 수
        String view = "";

        MemberEntity member = userDetails.getMemberEntity();

        Page<CommunityEntity> mycommunityBoard = mypageService.getCommunityPosts(member, page, pageSize);
        model.addAttribute("mycommunityBoard", mycommunityBoard);
        view = "mypage/community-board";

        return "mypage/mypage";
    }


    @GetMapping("/{type}-board")
    public String viewBoard(Model model, @PathVariable String type, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/member/loginForm";
        } else {
            Long memberId = userDetails.getMemberEntity().getId();
            System.out.println(">>>>>>>> : " + userDetails.getMemberEntity().getId());

            MemberEntity member = new MemberEntity();
            member.setId(memberId);

            int pageSize = 10;  // 한 페이지에 보여줄 게시물 수
            String view = "";

            if ("community".equals(type)) {
                Page<CommunityEntity> mycommunityBoard = mypageService.getCommunityPosts(member, page, pageSize);
                model.addAttribute("mycommunityBoard", mycommunityBoard);
                view = "mypage/community-board";
            } else if ("normal".equals(type)) {
                Page<NormalBoardEntity> mynormalBoard = mypageService.getNormalPosts(member, page, pageSize);
                model.addAttribute("mynormalBoard", mynormalBoard);
                view = "mypage/normal-board";
            } else if ("leader".equals(type)) {
                Page<LeaderBoardEntity> myleaderBoard = mypageService.getLeaderPosts(member, page, pageSize);
                model.addAttribute("myleaderBoard", myleaderBoard);
                view = "mypage/leader-board";
            }
            return view;
        }
    }


    /*@GetMapping("/normal-board")
    public String viewNormalBoard(Model model, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/member/loginForm";
        } else {
            Long memberId = userDetails.getMemberEntity().getId();
            System.out.println(">>>>>>>> : " + userDetails.getMemberEntity().getId());

            MemberEntity member = new MemberEntity();
            member.setId(memberId);

            int pageSize = 10;  // 한 페이지에 보여줄 게시물 수
            Page<NormalBoardEntity> mynormalBoard = mypageService.getNormalPosts(member, page, pageSize);
            System.out.println(mynormalBoard.getContent());

            model.addAttribute("mynormalBoard", mynormalBoard);
        }
        return "mypage/normal-board";
    }

    @GetMapping("/community-board")
    public String viewCommunityBoard(Model model, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/member/loginForm";
        } else {
            Long memberId = userDetails.getMemberEntity().getId();
            System.out.println(">>>>>>>> : " + userDetails.getMemberEntity().getId());

            MemberEntity member = new MemberEntity();
            member.setId(memberId);

            int pageSize = 10;  // 한 페이지에 보여줄 게시물 수
            Page<CommunityEntity> mycommunityBoard = mypageService.getCommunityPosts(member, page, pageSize);
            System.out.println(mycommunityBoard.getContent());

            model.addAttribute("mycommunityBoard", mycommunityBoard);
        }
        return "mypage/community-board";
    }

    @GetMapping("/leader-board")
    public String viewLeaderBoard(Model model, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/member/loginForm";
        } else {
            Long memberId = userDetails.getMemberEntity().getId();
            System.out.println(">>>>>>>> : " + userDetails.getMemberEntity().getId());

            MemberEntity member = new MemberEntity();
            member.setId(memberId);

            int pageSize = 10;  // 한 페이지에 보여줄 게시물 수
            Page<LeaderBoardEntity> myleaderBoard = mypageService.getLeaderPosts(member, page, pageSize);
            System.out.println(myleaderBoard.getContent());


            model.addAttribute("myleaderBoard", myleaderBoard);
            System.out.println(myleaderBoard);
            return "mypage/leader-board";
        }
    }*/
}