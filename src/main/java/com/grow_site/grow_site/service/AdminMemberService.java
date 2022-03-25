package com.grow_site.grow_site.service;

import com.grow_site.grow_site.DTO.admin.AdminMemberDetailDTO;
import com.grow_site.grow_site.DTO.article.ArticleDTO;
import com.grow_site.grow_site.DTO.article.ArticleListDTO;
import com.grow_site.grow_site.Dao.MemberRepository;
import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMemberList(){


        List<Member> members=memberRepository.findAll();

        return members;
    }

    public AdminMemberDetailDTO getMemberDetail(Long id){
        Optional<Member> memberOptional=memberRepository.findById(id);

        memberOptional.orElseThrow(
                ()->new IllegalStateException("존재하지 않는 회원입니다.")

        );

        Member member=memberOptional.get();

        List<Article> articles=member.getArticles();
        List<ArticleListDTO> articleDTOs=new ArrayList<>();

        for(Article article: articles){
            ArticleListDTO articleDTO=new ArticleListDTO(article);

            articleDTOs.add(articleDTO);

        }



        return new AdminMemberDetailDTO(member,articleDTOs);


    }


    @Transactional
    public void banMember(Long id){

        Optional<Member> memberOptional=memberRepository.findById(id);

        memberOptional.orElseThrow(
                ()->new IllegalStateException("존재하지 않는 회원입니다.")

        );

        Member member=memberOptional.get();
        memberRepository.delete(member);
    }
}
