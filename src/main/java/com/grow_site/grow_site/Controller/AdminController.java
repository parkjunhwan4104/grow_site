package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.Config.Role;
import com.grow_site.grow_site.DTO.admin.AdminMemberDetailDTO;
import com.grow_site.grow_site.domain.Member;
import com.grow_site.grow_site.service.AdminArticleService;
import com.grow_site.grow_site.service.AdminMemberService;
import com.grow_site.grow_site.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminMemberService adminMemberService;
    private final AdminArticleService articleService;

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
        model.addAttribute("id",adminMemberDetailDTO.getMemberId());

        return "admin/member/detail";


    }

    @PostMapping("/admin/members/detail/{id}")
    public String doUpdateGrade(@PathVariable(name="id")Long id, @RequestParam(name="authority")String authority){

        if(authority.equals("admin")){
            adminMemberService.updateGrade(id,Role.ADMIN);

        }
        else if(authority.equals("member")){
            adminMemberService.updateGrade(id,Role.MEMBER);
        }
        else{
            adminMemberService.updateGrade(id,Role.GUEST);
        }



        return "redirect:/admin/members/detail/"+id;
    }

    @GetMapping("/admin/members/ban/{id}")
    public String banMember(@PathVariable(name="id")Long id){

        adminMemberService.banMember(id);

        return "redirect:/admin/members";


    }

    @GetMapping("/admin/articles")
    public String showArticles(Model model){


        model.addAttribute("articles",articleService.getArticleList());

        return "admin/article/main";
    }

    @GetMapping("/admin/article/delete/{id}")
    public String deleteArticle(@PathVariable(name="id")Long id){

        articleService.deleteArticle(id);

        return "redirect:/admin/articles";
    }
}
