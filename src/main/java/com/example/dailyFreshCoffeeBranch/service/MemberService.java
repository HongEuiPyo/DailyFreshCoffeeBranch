package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.MemberFormDto;
import com.example.dailyFreshCoffeeBranch.dto.MemberUpdateFormDto;
import com.example.dailyFreshCoffeeBranch.domain.Address;
import com.example.dailyFreshCoffeeBranch.domain.Cart;
import com.example.dailyFreshCoffeeBranch.domain.Member;
import com.example.dailyFreshCoffeeBranch.exception.MemberNotFoundException;
import com.example.dailyFreshCoffeeBranch.repository.AddressRepository;
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
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 회원가입
     * @param memberFormDto
     * @return
     */
    @Transactional
    public void join(MemberFormDto memberFormDto) {
        memberFormDto.setPassword(bCryptPasswordEncoder.encode(memberFormDto.getPassword()));
        Member entity = memberFormDto.toEntity();

        validateDuplicatedMember(entity);

        Member member = memberRepository.save(entity);

        Address address = Address.builder()
                .roadAddress(memberFormDto.getRoadAddress())
                .latitude(memberFormDto.getLatitude())
                .longitude(memberFormDto.getLongitude())
                .member(member)
                .build();

        addressRepository.save(address);

        member.setAddress(address);

        Cart cart = cartRepository.findByMemberId(member.getId())
                        .orElseGet(() -> createMemberCart(member));

        member.setCart(cart);
    }

    /**
     * 이메일로 회원 상세 조회
     * @param email
     * @return
     */
    public MemberFormDto getMemberDetailByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원 상세정보가 없습니다. 괸리자에게 문의하세요."));
        return MemberFormDto.of(member);
    }

    /**
     * 아이디로 회원 상세 조회
     * @param memberId
     * @return
     */
    public MemberUpdateFormDto getMemberDetailByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("회원 상세정보가 없습니다. 괸리자에게 문의하세요."));
        return MemberUpdateFormDto.of(member);
    }

    /**
     * 회원정보 수정처리
     * @param id
     * @param updateDto
     */
    @Transactional
    public void updateMember(Long id, MemberUpdateFormDto updateDto) {
        updateDto.setPassword(bCryptPasswordEncoder.encode(updateDto.getPassword()));

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회하실 수 없습니다."));

        member.updateWithMemberUpdateDto(updateDto);
        memberRepository.save(member);

        if (member.getAddress() != null) {
            member.getAddress().update(updateDto);
        } else {
            Address address = Address.builder()
                    .latitude(updateDto.getLatitude())
                    .longitude(updateDto.getLongitude())
                    .roadAddress(updateDto.getRoadAddress())
                    .member(member)
                    .build();
            addressRepository.save(address);
        }

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