package com.example.smallpeopleblog.member;

import com.example.smallpeopleblog.dto.MemberDto;
import com.example.smallpeopleblog.entity.Member;
import com.example.smallpeopleblog.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     * @param joinFormDto
     * @return
     */
    public Member join(MemberDto joinFormDto) {
        joinFormDto.setPassword(bCryptPasswordEncoder.encode(joinFormDto.getPassword()));
        Member member = joinFormDto.toEntity();
        validateDuplicatedMember(member);
        return memberRepository.save(member);
    }

    /**
     * 이메일로 회원 상세 조회
     * @param email
     * @return
     */
    public MemberDto getMemberDetailByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원 상세정보가 없습니다. 괸리자에게 문의하세요."))
                .toDto();
    }

    /**
     * 아이디로 회원 상세 조회
     * @param memberId
     * @return
     */
    public MemberDto getMemberDetailByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원 상세정보가 없습니다. 괸리자에게 문의하세요."))
                .toDto();
    }

    /**
     * 중복회원 검증
     * @param member
     */
    public void validateDuplicatedMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(foundMember -> {
                    throw new RuntimeException("user already exists" + foundMember.getEmail());
                });
    }

    /**
     * 로그인 요청 검증을 위한 User 객체
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found user" + email));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}