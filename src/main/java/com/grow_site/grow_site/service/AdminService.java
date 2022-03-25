package com.grow_site.grow_site.service;


import com.grow_site.grow_site.Config.Role;
import com.grow_site.grow_site.DTO.admin.AdminMemberStateDTO;
import com.grow_site.grow_site.Dao.MemberRepository;
import com.grow_site.grow_site.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final MemberRepository memberRepository;

    public AdminMemberStateDTO getMemberStateDTO(){
        return new AdminMemberStateDTO(
                memberRepository.count(),
                memberRepository.countTodayMember(),
                memberRepository.countByAuthorityLike(Role.ADMIN),
                memberRepository.countByAuthorityLike(Role.MEMBER),
                memberRepository.countByAuthorityLike(Role.GUEST)

        );
    }
}
