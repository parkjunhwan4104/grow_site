package com.grow_site.grow_site.DTO.admin;

import com.grow_site.grow_site.Config.Role;
import com.grow_site.grow_site.DTO.article.ArticleListDTO;
import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminMemberDetailDTO {

    private Long  memberId;

    private String loginId;
    private String nickName;
    private LocalDateTime regDate;

    private Role authority;

    private List<ArticleListDTO> articleList;

    public AdminMemberDetailDTO(Member member, List<ArticleListDTO> articles){
        this.memberId=member.getId();
        this.loginId=member.getLoginId();
        this.nickName=member.getNickName();
        this.regDate=member.getRegDate();
        this.authority=member.getAuthority();
        this.articleList=articles;
    }
}
