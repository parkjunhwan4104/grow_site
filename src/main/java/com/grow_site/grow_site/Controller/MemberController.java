package com.grow_site.grow_site.Controller;

import com.grow_site.grow_site.DTO.member.*;
import com.grow_site.grow_site.domain.Member;
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



        model.addAttribute("memberSaveForm",new MemberSaveForm());
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

    @GetMapping("/members/login")
    public String showLogin(Model model){

        model.addAttribute("memberLoginForm",new MemberLoginForm());

        return "user/member/login";
    }

    @GetMapping("/error/deny")
    public String showError(){
        return "error/deny";
    }

    @GetMapping("/members/modify/{loginId}")
    public String showModify(@PathVariable(name="loginId")String loginId,Principal principal,Model model){

        Member findMember=memberService.findByLoginId(loginId);
        if(!findMember.getLoginId().equals(principal.getName())){
                return "redirect:/";
        }

        String LoginId=findMember.getLoginId();

        MemberModifyForm memberModifyForm=new MemberModifyForm(findMember);
        model.addAttribute("loginId",LoginId);
        model.addAttribute("memberModifyForm",memberModifyForm);


        return "user/member/modify";
    }

    @PostMapping("/members/modify/{loginId}")
    public String doModify(@PathVariable(name="loginId")String loginId,@Validated MemberModifyForm memberModifyForm,BindingResult bindingResult,Principal principal,Model model){

            if(bindingResult.hasErrors()){
                return "user/member/modify";
            }

            Member member=memberService.findByLoginId(loginId);
            if(!member.getLoginId().equals(principal.getName())){
                return "redirect:/";

            }

            try {
                memberService.modifyMember(memberModifyForm, loginId);
            }
            catch(Exception e){
                model.addAttribute("err_msg",e.getMessage());
                model.addAttribute("memberModifyForm",new MemberModifyForm(
                        member
                ));
                return "user/member/modify";
            }

            return "redirect:/";
    }

    @GetMapping("/members/mail/findPw")
    public String showChangePw(){

        return "user/member/findPw";
    }

    @ResponseBody
    @PostMapping("mails/find/pw")
    public boolean doChangePw(@RequestBody FindPasswordForm findPasswordForm){
        if(!memberService.isDupleLoginId(findPasswordForm.getLoginId())){
            return false;
        }
        try{
            memberService.sendMail(findPasswordForm);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


}























