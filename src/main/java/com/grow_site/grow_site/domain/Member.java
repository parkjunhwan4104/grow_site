package com.grow_site.grow_site.domain;


import com.grow_site.grow_site.Config.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor  //파라미터가 없는 생성자를 자동으로 생성합니다.
public class Member implements UserDetails {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String loginPw;


    private String nickName;
    private String email;



    private LocalDateTime regDate=LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Role authority;

    @OneToMany(mappedBy ="member",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)  //게시판 입장에서는 여러개의 게시물과 연관되어 있으므로 oneToMany 어노테이션을 사용하고  mappedBy를 통해 게시물이 게시판과 연관이 있음을 알려줌
    private List<Article> articles=new ArrayList<>();

    private boolean isAccountNonExpired= true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;
    private boolean isEnabled=true;

    public static Member createMember(String loginId,String loginPw,String nickName,String email,Role authority){

        Member member=new Member();
        member.loginId=loginId;
        member.loginPw=loginPw;
        member.nickName=nickName;
        member.email=email;

        member.authority=authority;

        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.authority.getValue()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPw;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void modifyMember(String loginPw,String nickName,String email){
        this.loginPw=loginPw;
        this.nickName=nickName;
        this.email=email;

    }

    public void setAuthority(Role authority){
        this.authority=authority;
    }
}
