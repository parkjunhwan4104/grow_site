package com.grow_site.grow_site.service;

import com.grow_site.grow_site.Config.Role;
import com.grow_site.grow_site.DTO.member.FindPasswordForm;
import com.grow_site.grow_site.DTO.member.MemberLoginForm;
import com.grow_site.grow_site.DTO.member.MemberModifyForm;
import com.grow_site.grow_site.DTO.member.MemberSaveForm;
import com.grow_site.grow_site.Dao.MemberRepository;
import com.grow_site.grow_site.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor //초기화되지 않은 final 필드에 대해 생성자를 생성해줌
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS="wnsghks4104@gmail.com";


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {




           return memberRepository.findByLoginId(userName).get();

    }



    public void isDuplicateMember(String loginId, String nickname,String email){
        if(memberRepository.existsByLoginId(loginId)){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");

        }
        else if(memberRepository.existsByNickName(nickname)){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
        else if(memberRepository.existsByEmail(email)){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

    }


    @Transactional
    public void save(MemberSaveForm memberSaveForm) throws IllegalStateException{

        isDuplicateMember(
                memberSaveForm.getLoginId(),
                memberSaveForm.getNickName(),
                memberSaveForm.getEmail()
        );



        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        Member member= Member.createMember(
                memberSaveForm.getLoginId(),
                bCryptPasswordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getNickName(),
                memberSaveForm.getEmail(),

                Role.GUEST


        );
        memberRepository.save(member);

    }


    public Member findById(Long id) throws IllegalStateException{

        Optional<Member> memberOption=memberRepository.findById(id);
        memberOption.orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 회원입니다.")
        );
        return memberOption.get();

    }




    public Member findByLoginId(String loginId) throws IllegalStateException{

        Optional<Member> memberOption=memberRepository.findByLoginId(loginId);
        memberOption.orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 회원입니다.")
        );
        return memberOption.get();

    }

    public Member getMember(String loginId){
        Member member=findByLoginId(loginId);
        return member;
    }

    public boolean isDupleLoginId(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }

    public boolean isDupleNickName(String nickName){
        return memberRepository.existsByNickName(nickName);
    }

    public boolean isDupleEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void modifyMember(MemberModifyForm memberModifyForm,String loginId){

        Member findMember=findByLoginId(loginId);
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        findMember.modifyMember(
                bCryptPasswordEncoder.encode(memberModifyForm.getLoginPw()),
                memberModifyForm.getNickName(),
                memberModifyForm.getEmail()


        );



    }

    @Transactional
    public void changePw(String password,Member member){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        member.changePw(bCryptPasswordEncoder.encode(password));
    }


    @Transactional
    public void sendMail(FindPasswordForm findPasswordForm) {
        Member findMember =findByLoginId(findPasswordForm.getLoginId());
        System.out.println(findMember.getLoginPw());
        String uuid= UUID.randomUUID().toString();
        String tempPw=uuid.substring(0,5);
        changePw(tempPw,findMember);
        System.out.println(findMember.getLoginPw());
        try{
            MimeMessage mail=javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");
            mailHelper.setFrom(FROM_ADDRESS);  //누가 보낼것인지
            mailHelper.setTo(findPasswordForm.getEmail());  //누가 받을 것인지
            mailHelper.setSubject("GROW에서 임시 비밀번호를 보내드립니다.");
            mailHelper.setText(findMember.getNickName()+"님의 임시 비밀번호는"+tempPw+"입니다.\r\n 로그인 후 비밀번호를 변경해 주시기 바랍니다.");
            javaMailSender.send(mail);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}


















































