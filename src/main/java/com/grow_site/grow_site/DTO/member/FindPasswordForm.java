package com.grow_site.grow_site.DTO.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordForm {


    private String loginId;


    private String email;
}
