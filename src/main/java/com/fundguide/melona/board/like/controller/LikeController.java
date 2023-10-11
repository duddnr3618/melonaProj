package com.fundguide.melona.board.like.controller;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.board.like.Service.LikeService;
import com.fundguide.melona.board.like.dto.LikeDto;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.service.CustomUSerDetailService;
import com.fundguide.melona.member.service.CustomUserDetails;
import com.fundguide.melona.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.Map;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;



}
