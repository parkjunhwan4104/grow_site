package com.grow_site.grow_site.DTO.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class MemberLoginForm {


    private String loginId;


    private String loginPw;
}
