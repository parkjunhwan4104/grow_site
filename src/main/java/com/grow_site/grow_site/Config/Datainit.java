package com.grow_site.grow_site.Config;

import com.grow_site.grow_site.Dao.MemberRepository;
import com.grow_site.grow_site.domain.Member;
import com.grow_site.grow_site.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Datainit {

    private final InitService initService;
    private final MemberRepository memberRepository;
    @PostConstruct
    public void init(){

        if(!memberRepository.existsByLoginId("wnsghks4104")){
            initService.initAdmin();
        }

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final MemberRepository memberRepository;

        public void initAdmin(){

            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            Member admin= Member.createMember(
              "wnsghks4104",
              bCryptPasswordEncoder.encode("admin"),
              "201902695 박준환",
              "wnsghks4104@gmail.com",
              Role.ADMIN

            );
            memberRepository.save(admin);
        }
    }
}



























