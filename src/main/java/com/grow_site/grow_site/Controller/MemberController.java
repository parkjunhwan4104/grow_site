package com.grow_site.grow_site.Controller;

import com.grow_site.grow_site.DTO.member.CheckStatus;
import com.grow_site.grow_site.DTO.member.MemberSaveForm;
import com.grow_site.grow_site.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;  //final을 붙여 Controller 내부에서 service 객체를 바꿔치기 할 수 없도록 함


    @RequestMapping("/members/check/id")
    @ResponseBody
    public CheckStatus checkDupleLoginId(@RequestParam String loginId){
        boolean isExist=memberService.isDupleLoginId(loginId);

        CheckStatus checkStatus=new CheckStatus(isExist);

        return checkStatus;

    }

    @RequestMapping("/members/check/email")
    @ResponseBody //위의 url을 보지 않고 처리
    public CheckStatus checkEmail(@RequestParam String email){
        boolean isExist=memberService.isDupleEmail(email);

        CheckStatus checkStatus=new CheckStatus(isExist);

        return checkStatus;
    }



    @RequestMapping("/members/check/nickName")
    @ResponseBody
    public CheckStatus checkDupleNickName(@RequestParam String nickName){
        boolean isExist=memberService.isDupleNickName(nickName);
        CheckStatus checkStatus=new CheckStatus(isExist);
        return checkStatus;
    }

    @GetMapping("/members/join")
    public String showJoin(Model model){
        MemberSaveForm memberSaveForm=new MemberSaveForm();

        int a=1;

        model.addAttribute("number1",a);

        model.addAttribute("memberSaveForm",memberSaveForm);
        return "user/member/join";
    }

    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){
            return "user/member/join";
        }

        try{
            memberService.save(memberSaveForm);
        }
        catch(Exception e){
            model.addAttribute("err_msg",e.getMessage());
        }



        return "redirect:/";
    }
}























