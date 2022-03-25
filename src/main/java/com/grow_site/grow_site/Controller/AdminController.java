package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.DTO.admin.AdminMemberDetailDTO;
import com.grow_site.grow_site.domain.Member;
import com.grow_site.grow_site.service.AdminMemberService;
import com.grow_site.grow_site.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminMemberService adminMemberService;

    @GetMapping("/admin/main")
    public String showMain(Model model){

        model.addAttribute("memberStateData",adminService.getMemberStateDTO());


        return "admin/mainPage";
    }

    @GetMapping("/admin/members")
    public String showMember(Model model){
        List<Member> memberList=adminMemberService.getMemberList();

        model.addAttribute("memberList",memberList);


        return "admin/member/main";
    }


    @GetMapping("/admin/members/detail/{id}")
    public String showDetail(@PathVariable(name="id")Long id,Model model){

        AdminMemberDetailDTO adminMemberDetailDTO=adminMemberService.getMemberDetail(id);
        model.addAttribute("member",adminMemberDetailDTO);

        return "admin/member/detail";


    }

    @GetMapping("/admin/members/ban/{id}")
    public String banMember(@PathVariable(name="id")Long id){

        adminMemberService.banMember(id);

        return "redirect:/admin/members";


    }
}
