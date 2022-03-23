package com.grow_site.grow_site.DTO.member;


import com.grow_site.grow_site.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MemberModifyForm {

    @NotBlank(message="비밀번호를 입력해 주세요")
    private String loginPw;

    @NotBlank(message="닉네임를 입력해 주세요")
    private String nickName;

    @NotBlank(message="이메일를 입력해 주세요")
    private String email;

    public MemberModifyForm(Member member){
        this.loginPw=member.getLoginPw();
        this.nickName=member.getNickName();
        this.email=member.getEmail();


    }



}
