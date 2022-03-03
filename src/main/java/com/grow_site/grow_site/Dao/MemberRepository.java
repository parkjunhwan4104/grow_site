package com.grow_site.grow_site.Dao;

import com.grow_site.grow_site.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

    boolean existsByNickName(String nickName);
    boolean existsByEmail(String email);
}
