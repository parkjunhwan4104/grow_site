package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.domain.Member;
import com.grow_site.grow_site.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class HomeController {



    @GetMapping("/")
    public String showIndexPage(){



        return "index";
    }


}
