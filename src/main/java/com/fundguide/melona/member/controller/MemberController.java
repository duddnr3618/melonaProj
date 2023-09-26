package com.fundguide.melona.member.controller;

import com.fundguide.melona.member.dto.MemberDto;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.mapper.MemberTransMapper;
import com.fundguide.melona.member.service.CustomUserDetails;
import com.fundguide.melona.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
//@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("member/loginForm")   // 로그인 페이지로 가는거
    public String loginForm() {
        return "member/loginForm";
    }


    @GetMapping("member/joinForm")  // 회원가입페이지로 가는거
    public String joinForm(@ModelAttribute MemberDto memberDto, Model model) {
        model.addAttribute("memberDto", memberDto);
        return "member/joinFrom";
    }

    @PostMapping("member/JoinPro")      // 회원가입 요청
    public String memberSave(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "member/joinFrom";
        memberService.memberSave(memberDto);
        return "redirect:/";
    }

    @GetMapping("success")    // 로그인 성공테스트용
    public String success() {
        return "member/success";
    }

    @GetMapping("fail")     // 로그인 실패 테스트
    public String fail() {
        return "member/fail";
    }

    @GetMapping("memberUpdateForm")   // 회원정보 수정 페이지 이동
    public String memberUpdate(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        MemberEntity memberEntity = customUserDetails.getMemberEntity();
        MemberDto memberDto = MemberTransMapper.INSTANCE.entityToDto(memberEntity);
        model.addAttribute("memberDto", memberDto);
        return "member/updateForm";
    }

    @PostMapping("member/duplicatedCheck")    // 이름,닉네임,중복체크 쿼리dsl로 둘중 값이 있는거 중복체크
    @ResponseBody
    public String duplicatedCheck(@RequestBody MemberDto memberDto) {
        MemberDto findDto = memberService.duplicatedCheck(memberDto);
        if (findDto == null)
            return "ok";
        else
            return "no";
    }

    @GetMapping("member/sendConfirmEmail")          // 인증번호 이메일 발송
    @ResponseBody
    public String sendConfirmEmail(@RequestParam String memberEmail) {
        String result = memberService.sendConfirmEmail(memberEmail);
        return result;
    }

    @GetMapping("member/findInfoForm")              // id/pw 찾는 폼으로 이동
    public String findInfo() {
        return "member/findInfoForm";
    }
    @PostMapping("member/findEmailByNickname")  // id/pw 찿는폼에서 닉네임으로 이메일찾기
    @ResponseBody
    public String findEmailByNickname(@RequestBody MemberDto memberDto){
        MemberDto findDto = memberService.duplicatedCheck(memberDto);
        String result;
        if(findDto!=null && findDto.getMemberEmail()!=null){
            result=findDto.getMemberEmail();
        }else{
            result = "가입된 이메일이 없습니다.닉네임을 다시 확인해 주세요.";
        }
        return result;
    }

    @GetMapping("member/findPassword")        //비밀번호 찾기 => 랜덤 uuid 로 비밀번호 변경후 변경되 비밀번호 발송
    @ResponseBody
    public String findPassword(@RequestParam String memberEmail) {
        memberService.findPassword(memberEmail);
        return "ok";
    }
    @GetMapping("user/member/beforeChangePasswordForm")  // 탈퇴/비밀번호 변경전에 본인확인폼
    public String beforeChangePasswordForm(@AuthenticationPrincipal CustomUserDetails customUserDetails,Model model,RedirectAttributes redirectAttributes){
        if(redirectAttributes.getAttribute("wrong")!=null){
            model.addAttribute("wrong",redirectAttributes.getAttribute("wrong"));
        }
        model.addAttribute("id", customUserDetails.getMemberEntity().getId());
        return "member/beforChangePasswordForm";
    }
    @PostMapping("user/member/passwordConfirmPro")   // 탈퇴/비밀번호 변경전 본인확인
    public String passwordConfirmPro(@ModelAttribute MemberDto memberDto,RedirectAttributes redirectAttributes){
        boolean result = memberService.beforeMemberDelteCheckPassword(memberDto);
        if(result) return "redirect:/";
        else {
            redirectAttributes.addAttribute("wrong","비밀번호를확인하세요." );
            return "redirect:/user/member/beforeChangePasswordForm";
        }
        }
    @GetMapping("user/member/updateForm")   // 회원정보수정 폼으로 이동
    public String memberUpdateForm(Model model,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long id = customUserDetails.getMemberEntity().getId();
        MemberDto memberDto = memberService.findById(id);
        model.addAttribute("memberDto", memberDto);
        return "member/memberUpdateForm";
    }
    @PostMapping("/user/member/updatePro")  // 회원정보수정
    public String memberUpdate(@ModelAttribute MemberDto memberDto){
        memberService.memberUpdate(memberDto);
        return "redirect:/";
    }
}
