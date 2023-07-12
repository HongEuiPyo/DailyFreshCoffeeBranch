package com.example.smallpeopleblog.service;

import com.example.smallpeopleblog.dto.CartDto;
import com.example.smallpeopleblog.dto.CartItemDto;
import com.example.smallpeopleblog.dto.CartItemUpdateDto;
import com.example.smallpeopleblog.entity.Cart;
import com.example.smallpeopleblog.entity.CartItem;
import com.example.smallpeopleblog.entity.Item;
import com.example.smallpeopleblog.entity.Member;
import com.example.smallpeopleblog.exception.CartItemNotFoundException;
import com.example.smallpeopleblog.exception.CartNotFoundException;
import com.example.smallpeopleblog.exception.ItemNotFoundException;
import com.example.smallpeopleblog.exception.MemberNotFoundException;
import com.example.smallpeopleblog.repository.CartItemRepository;
import com.example.smallpeopleblog.repository.CartRepository;
import com.example.smallpeopleblog.repository.ItemRepository;
import com.example.smallpeopleblog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;


    /**
     * 장바구니 상품 등록
     * @param email
     * @param itemId
     * @param cartItemDto
     * @return
     */
    @Transactional
    public void addToCart(String email, Long itemId, CartItemDto cartItemDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseGet(() -> createMemberCart(member));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("상품을 조회할 수 없습니다."));

        Optional<CartItem> cartItemOptional = cartItemRepository.findByItemId(itemId);
        cartItemOptional.ifPresentOrElse(
                cartItem -> cartItem.addCount(cartItemDto.getCount()),
                () -> cartItemRepository.save(cartItemDto.toEntity(cart, item)));

    }

    /**
     * 장바구니 상품 목록
     * @param email
     * @return
     */
    public CartDto getCartItemList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .member(member)
                        .build()));

        return cart.toDto();
    }

    /**
     * 장바구니 상품 수정
     * @param email
     * @param cartItemId
     * @param cartItemUpdateDto
     * @return
     */
    @Transactional
    public CartItemDto updateCartItem(String email, Long cartItemId, CartItemUpdateDto cartItemUpdateDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니를 조회할 수 없습니다."));

        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new CartItemNotFoundException("장바구니 상품을 조회할 수 없습니다."));

        cartItem.setCount(cartItemUpdateDto.getCount());
        cartItemRepository.save(cartItem);

        return cartItem.toDto();
    }

    /**
     * 장바구니 상품 삭제
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

    /**
     * 회원 장바구니 생성
     * @param member
     * @return
     */
    private Cart createMemberCart(Member member) {
        Cart cart = Cart.builder()
                .member(member)
                .build();
        return cartRepository.save(cart);
    }

}