package com.grow_site.grow_site.DTO.member;

import lombok.Data;
import javax.validation.constraints.NotBlank;
@Data
public class MemberSaveForm {

    @NotBlank(message="아이디를 입력해 주세요")
    private String loginId;

    @NotBlank(message="비밀번호를 입력해 주세요")
    private String loginPw;

    @NotBlank(message="닉네임를 입력해 주세요")
    private String nickName;

    @NotBlank(message="이메일를 입력해 주세요")
    private String email;

}























