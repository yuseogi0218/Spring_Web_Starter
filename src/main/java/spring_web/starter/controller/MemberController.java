package spring_web.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring_web.starter.domain.Member;
import spring_web.starter.service.MemberService;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/login")
    public String login(MemberForm form, Model model, RedirectAttributes redirectAttributes) {
        String result = memberService.login(form.getUser_id(), form.getUser_pass());
        if (result.equals("pass_error") || result.equals("null")) {
            redirectAttributes.addFlashAttribute("errorMessage", result);
            return "redirect:/";
        } else {
            model.addAttribute("user_id", form.getUser_id());
            model.addAttribute("user_pass", form.getUser_pass());
            model.addAttribute("user_name", result);
            return "main";
        }
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "member/createMemberForm";
    }

    @PostMapping("/signUp")
    public String signUp(MemberForm form, RedirectAttributes redirectAttributes, Model model) {
        System.out.println(form.getUser_id());
        if (!(form.getUser_pass()).equals(form.getPass_chk())) {
            redirectAttributes.addFlashAttribute("errorMessage", "pass_error");
            return "redirect:/signUp";
        }
        Member member = new Member();
        member.setUser_id(form.getUser_id());
        member.setUser_pass(form.getUser_pass());
        member.setUser_name(form.getUser_name());
        try {
            memberService.signUp(member);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/signUp";
        }
        model.addAttribute("user_id", form.getUser_id());
        model.addAttribute("user_pass", form.getUser_pass());
        model.addAttribute("user_name", form.getUser_name());
        return "main";
    }

    @PostMapping("/delete")
    public String signOut(MemberForm form) {
        memberService.signOut(form.getUser_id());
        return "redirect:/";
    }
}
