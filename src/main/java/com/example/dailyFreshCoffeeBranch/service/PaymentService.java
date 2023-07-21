package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.api.NaverDirection5Api;
import com.example.dailyFreshCoffeeBranch.com.VIPDiscountPolicy;
import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import com.example.dailyFreshCoffeeBranch.dto.Directions5RequestDto;
import com.example.dailyFreshCoffeeBranch.dto.MemberPointUpDto;
import com.example.dailyFreshCoffeeBranch.dto.PaymentDto;
import com.example.dailyFreshCoffeeBranch.entity.*;
import com.example.dailyFreshCoffeeBranch.exception.CartNotFoundException;
import com.example.dailyFreshCoffeeBranch.exception.MemberNotFoundException;
import com.example.dailyFreshCoffeeBranch.exception.OutOfPointException;
import com.example.dailyFreshCoffeeBranch.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentRepository paymentRepository;
    private final ItemRepository itemRepository;
    private final VIPDiscountPolicy vipDiscountPolicy;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryItemRepository deliveryItemRepository;
    private final AddressQueryDslRepository addressQueryDslRepository;
    private final NaverDirection5Api naverDirection5Api;

    /**
     * 구매 상품 목록 조회하기
     *
     * @param email
     * @return
     */
    public List<PaymentDto> getPaymentList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));
        List<Payment> paymentList = paymentRepository.findByMemberId(member.getId());
        return paymentList.stream()
                .map(Payment::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 포인트 충전하기
     *
     * @param email
     * @param memberPointUpDto
     * @return
     */
    @Transactional
    public void addPoint(String email, MemberPointUpDto memberPointUpDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));
        member.addPoint(memberPointUpDto.getPoint());
    }

    /**
     * 구매 확정하기
     *
     * @param email
     * @param paymentDto
     */
    @Transactional
    public void confirmCartItemPurchase(String email, PaymentDto paymentDto) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        pay(paymentDto, member);

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니를 찾을 수 없습니다."));

        deliverCartItem(member, cart);

        clearCart(cart);
    }

    /**
     * 결제하기
     *
     * @param paymentDto
     * @param member
     */
    private void pay(PaymentDto paymentDto, Member member) {
        double discountPrice = ifVIPGetDiscountPrice(paymentDto, member);
        member.payPoint(discountPrice);

        if(discountPrice != paymentDto.getTotalUsePoint()){
            paymentDto.setDiscount(vipDiscountPolicy.getDiscountRate());
        }
        Payment payment = paymentDto.toEntity(member);
        paymentRepository.save(payment);
    }

    /**
     * 모든 장바구니 상품 배송하기
     *
     * @param member
     * @param cart
     */
    private void deliverCartItem(Member member, Cart cart) {

        Address storeLoc = addressQueryDslRepository.getStoreLocation();
        Address userLoc = addressQueryDslRepository.getLoginUserLocation(member.getEmail());

        Directions5RequestDto directions5RequestDto = Directions5RequestDto.builder()
                .startLatitude(storeLoc.getLatitude())
                .startLongitude(storeLoc.getLongitude())
                .goalLatitude(userLoc.getLatitude())
                .goalLongitude(userLoc.getLongitude())
                .build();

        long duration = naverDirection5Api.getDuration(directions5RequestDto);

        Delivery delivery = Delivery.builder()
                .deliveryStatus(DeliveryStatus.DELIVERING)
                .member(member)
                .deliveryDepartureRoadLocation(storeLoc.getRoadAddress())
                .deliveryDestinationRoadLocation(userLoc.getRoadAddress())
                .deliveryTakenTime(duration)
                .build();

        delivery = deliveryRepository.save(delivery);

        for (CartItem cartItem : cart.getCartItemList()) {
            DeliveryItem deliveryItem = DeliveryItem.builder()
                    .delivery(delivery)
                    .item(cartItem.getItem())
                    .count(cartItem.getCount())
                    .deliveryItemStatus(DeliveryItemStatus.DELIVERING)
                    .build();

            deliveryItemRepository.save(deliveryItem);
        }

    }

    /**
     * 장바구니 상품 목록 비우기
     *
     * @param cart
     */
    private void clearCart(Cart cart) {

        for (CartItem cartItem : cart.getCartItemList()) {
            Item item = cartItem.getItem();
            item.decreaseStock(cartItem.getCount());
            item.addPurchaseCount(cartItem.getCount());
            itemRepository.save(item);
        }

        cartItemRepository.deleteAll(cart.getCartItemList());

    }

    /**
     * VIP일 시 혜택가 적용하기
     *
     * @param paymentDto
     * @param member
     * @return
     */
    private double ifVIPGetDiscountPrice(PaymentDto paymentDto, Member member) {

        double discountPrice = vipDiscountPolicy.getDiscountPrice(paymentDto.getTotalUsePoint(), member.getRole());

        if(member.getPoint() < discountPrice){
            throw new OutOfPointException("포인트가 부족합니다.");
        }
        return discountPrice;
    }

}