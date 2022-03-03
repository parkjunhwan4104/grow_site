package com.grow_site.grow_site.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {  //시큐리티가 관리하는 파일들을 정함
        web.ignoring().mvcMatchers("/css/**", "/js/**", "/img/**", "/error/**", "/lib/**"); //시큐리티가 우리가 가지고 있는 파일들을 모두 관리해줌
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize ->authorize

<<<<<<< HEAD
                .mvcMatchers("/").anonymous() //URL이 간소화될수있도록함,  회원가입하는 url, 어나니머스는 로그인이 되지 않은 사람도 해당 페이지를 들어갈수 있도록함
=======
                .mvcMatchers("/","/members/join","/members/login","/members/check/**").anonymous() //URL이 간소화될수있도록함,  회원가입하는 url, 어나니머스는 로그인이 되지 않은 사람도 해당 페이지를 들어갈수 있도록함
>>>>>>> 5a94136 (Member 로직 구현 및 회원가입 기능 구현 및 index페이지 ui 구현)
                .mvcMatchers("/aritcles/**").permitAll() // 로그인한 사람만 게시글에 대한 기능을 사용할 수 있도록 하는거

                .anyRequest()
                .denyAll() //위의 3개 페이지말고는 모두 다 거절해라
        )
                .formLogin()
                .loginPage("/members/login")
                .loginProcessingUrl("/doLogin")   //로그인이 이루어지는 페이지
                .usernameParameter("loginId")
                .passwordParameter("loginPw")
                .defaultSuccessUrl("/")         //로그인 성공후에 인덱스 페이지로 보내줌
                .and()
                .logout()

                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)  //권한 정보를 삭제(true하면)
                .and()
                .sessionManagement()
                .invalidSessionUrl("/") //유효하지 않은 세션이면 인덱스 페이지로 넘어감
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)  //동시접속을 차단해줌
                .expiredUrl("/");  //세션이 만료시에 이동할 url을 정해줌


    }
}