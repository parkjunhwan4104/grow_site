package com.grow_site.grow_site.Dao;

import com.grow_site.grow_site.Config.Role;
import com.grow_site.grow_site.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsByNickName(String nickName);
    boolean existsByEmail(String email);

    @Query(value="SELECT COUNT(*) FROM `member` WHERE DATE_FORMAT(reg_date, '%y-%m-%d')= CURDATE()",nativeQuery = true)  //nativeQuery가 true이면 일반적인 SQL언어라는 것이고 false이면 JPA의 일부분인 JSQL로 쿼리로 작성한다는거
    Long countTodayMember();

    Long countByAuthorityLike(Role authority);

}
