package com.grow_site.grow_site.DTO.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordForm {

    @NotBlank(message="아이디를 입력해 주세요")
    private String loginId;

    @NotBlank(message="이메일을 입력해 주세요")
    private String email;
}
