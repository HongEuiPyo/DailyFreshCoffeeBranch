package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.CartItemFormDto;
import com.example.dailyFreshCoffeeBranch.dto.CartItemUpdateFormDto;
import com.example.dailyFreshCoffeeBranch.dto.CartResponseDto;
import com.example.dailyFreshCoffeeBranch.domain.Cart;
import com.example.dailyFreshCoffeeBranch.domain.CartItem;
import com.example.dailyFreshCoffeeBranch.domain.Item;
import com.example.dailyFreshCoffeeBranch.domain.Member;
import com.example.dailyFreshCoffeeBranch.exception.CartItemNotFoundException;
import com.example.dailyFreshCoffeeBranch.exception.CartNotFoundException;
import com.example.dailyFreshCoffeeBranch.exception.ItemNotFoundException;
import com.example.dailyFreshCoffeeBranch.exception.MemberNotFoundException;
import com.example.dailyFreshCoffeeBranch.repository.CartItemRepository;
import com.example.dailyFreshCoffeeBranch.repository.CartRepository;
import com.example.dailyFreshCoffeeBranch.repository.ItemRepository;
import com.example.dailyFreshCoffeeBranch.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CartItemService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;


    /**
     * 장바구니 상품 등록
     *
     * @param email
     * @param itemId
     * @param cartItemFormDto
     * @return
     */
    @Transactional
    public void addToCart(String email, Long itemId, CartItemFormDto cartItemFormDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        Cart cart = getCartOrElseCreate(member);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 조회할 수 없습니다."));

//        cartItemRepository.findByItemId(itemId)
//                .ifPresentOrElse(
//                        cartItem -> cartItem.addCount(cartItemFormDto.getCount()), // 이미 상품 존재 시 수량 증가
//                        () -> cartItemRepository.save(cartItemFormDto.toEntity(cart, item))); // 없을 시 장바구니에 상품 추가;

        cartItemRepository.findByItemIdAndCartId(itemId, cart.getId())
                .ifPresentOrElse(
                        cartItem -> cartItem.addCount(cartItemFormDto.getCount()), // 이미 상품 존재 시 수량 증가
                        () -> cartItemRepository.save(cartItemFormDto.toEntity(cart, item))); // 없을 시 장바구니에 상품 추가;

    }

    /**
     * 장바구니 조회
     *
     * @param member
     * @return
     */
    private Cart getCartOrElseCreate(Member member) {
        return cartRepository.findByMemberId(member.getId())
                .orElseGet(() -> {
                    Cart c = Cart.builder()
                            .member(member)
                            .build();
                    return cartRepository.save(c);
                });
    }

    /**
     * 장바구니 상품 목록
     *
     * @param email
     * @return
     */
    public CartResponseDto getCartItemList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        Cart cart = getCartOrElseCreate(member);

        return cart.toResponseDto();
    }

    /**
     * 장바구니 상품 수정
     *
     * @param email
     * @param cartItemId
     * @param cartItemUpdateFormDto
     * @return
     */
    @Transactional
    public CartItemFormDto updateCartItem(String email, Long cartItemId, CartItemUpdateFormDto cartItemUpdateFormDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니를 조회할 수 없습니다."));

        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new CartItemNotFoundException("장바구니 상품을 조회할 수 없습니다."));

        cartItem.setCount(cartItemUpdateFormDto.getCount());
        cartItemRepository.save(cartItem);

        return cartItem.toDto();
    }

    /**
     * 장바구니 상품 삭제
     *
     * @param cartItemId
     * @param email
     */
    @Transactional
    public void deleteCartItem(Long cartItemId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니를 조회할 수 없습니다."));

        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new CartItemNotFoundException("장바구니 상품을 조회할 수 없습니다."));

        cartItemRepository.delete(cartItem);
    }

}