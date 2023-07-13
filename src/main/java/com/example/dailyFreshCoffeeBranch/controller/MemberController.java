package com.example.dailyFreshCoffeeBranch.controller;

import com.example.dailyFreshCoffeeBranch.dto.MemberDto;
import com.example.dailyFreshCoffeeBranch.dto.MemberUpdateDto;
import com.example.dailyFreshCoffeeBranch.security.SessionUser;
import com.example.dailyFreshCoffeeBranch.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;


    /**
     * 로그인
     * @return
     */
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    /**
     * 로그인 실패
     * @return
     */
    @GetMapping("/loginFail")
    public String loginFail(Model model) {
        model.addAttribute("loginFailMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/login";
    }

    /**
     * 회원 폼
     * @return
     */
    @GetMapping("/join")
    public String join(@ModelAttribute(name = "memberDto") MemberDto memberDto) {
        return "member/join";
    }

    /**
     * 회원가입 처리
     * @param memberDto
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/generalJoin")
    public String generalJoin(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        try {
            memberService.join(memberDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join";
        }

        return "redirect:/members/login";
    }

    /**
     * 회원 상세
     * @param principal
     * @param model
     * @return
     */
    @GetMapping("/myPage")
    public String memberDetail(Principal principal, Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String memberEmail = "";
        if (user == null) {
            memberEmail = principal.getName();
        } else {
            memberEmail = user.getEmail();
        }
        model.addAttribute("memberDto", memberService.getMemberDetailByEmail(memberEmail));
        return "member/memberDetail";
    }

    /**
     * 회원 수정 폼
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/update")
    public String memberForm(@PathVariable Long id, Model model) {
        model.addAttribute("memberUpdateDto", memberService.getMemberDetailByMemberId(id));
        return "member/memberForm";
    }

    /**
     * 회원 수정 처리
     * @param id
     * @return
     */
    @PostMapping("/{id}/update")
    public String updateMember(@PathVariable Long id, @Valid MemberUpdateDto memberUpdateDto, BindingResult result) {
        if (result.hasErrors())
            return "member/memberForm";

        memberService.updateMember(id, memberUpdateDto);

        return "redirect:/members/logout";
    }

    /**
     * 회원 삭제 처리
     * @param id
     * @return
     */
    @PostMapping("/{id}/delete")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/";
    }

}