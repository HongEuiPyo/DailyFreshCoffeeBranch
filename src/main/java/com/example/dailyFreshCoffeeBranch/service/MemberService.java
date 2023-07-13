package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.MemberDto;
import com.example.dailyFreshCoffeeBranch.dto.MemberUpdateDto;
import com.example.dailyFreshCoffeeBranch.entity.Cart;
import com.example.dailyFreshCoffeeBranch.entity.Member;
import com.example.dailyFreshCoffeeBranch.exception.MemberNotFoundException;
import com.example.dailyFreshCoffeeBranch.repository.CartRepository;
import com.example.dailyFreshCoffeeBranch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 회원가입
     * @param memberDto
     * @return
     */
    @Transactional
    public void join(MemberDto memberDto) {
        memberDto.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
        Member entity = memberDto.toEntity();

        validateDuplicatedMember(entity);

        Member member = memberRepository.save(entity);

        Cart cart = cartRepository.findByMemberId(member.getId())
                        .orElseGet(() -> createMemberCart(member));

        member.setCart(cart);
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
    public MemberUpdateDto getMemberDetailByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원 상세정보가 없습니다. 괸리자에게 문의하세요."));
        return MemberUpdateDto.of(member);
    }

    /**
     * 회원정보 수정처리
     * @param id
     * @param memberUpdateDto
     */
    @Transactional
    public void updateMember(Long id, MemberUpdateDto memberUpdateDto) {
        memberUpdateDto.setPassword(bCryptPasswordEncoder.encode(memberUpdateDto.getPassword()));

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회하실 수 없습니다."));

        member.updateWithMemberUpdateDto(memberUpdateDto);
        memberRepository.save(member);
    }

    /**
     * 회원정보 삭제처리
     * @param id
     */
    @Transactional
    public void deleteMember(Long id) {
        Cart cart = cartRepository.findByMemberId(id)
                .orElseThrow();
        cartRepository.deleteById(cart.getId());

        memberRepository.deleteById(id);
    }

    /**
     * 중복회원 검증
     * @param member
     */
    private void validateDuplicatedMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(foundMember -> {
                    throw new RuntimeException("user already exists" + foundMember.getEmail());
                });
    }

    /**
     * 회원 장바구니 생성
     * @param member
     * @return
     */
    private Cart createMemberCart(Member member) {
        Cart cart = new Cart(member);
        return cartRepository.save(cart);
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