package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.api.NaverApiDirection5;
import com.example.dailyFreshCoffeeBranch.com.VIPDiscountPolicy;
import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import com.example.dailyFreshCoffeeBranch.dto.Directions5RequestDto;
import com.example.dailyFreshCoffeeBranch.dto.PaymentDto;
import com.example.dailyFreshCoffeeBranch.entity.*;
import com.example.dailyFreshCoffeeBranch.exception.CartNotFoundException;
import com.example.dailyFreshCoffeeBranch.exception.MemberNotFoundException;
import com.example.dailyFreshCoffeeBranch.exception.OutOfPointException;
import com.example.dailyFreshCoffeeBranch.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class DeliveryApiService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final VIPDiscountPolicy vipDiscountPolicy;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryItemRepository deliveryItemRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    private final NaverApiDirection5 naverApiDirection5;


    /**
     * 장바구니 상품 배송 확정
     *
     * @param email
     * @param paymentDto
     */
    @Transactional
    public void deliverCartItems(String email, PaymentDto paymentDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        pay(paymentDto, member); // 결제정보 회원 entity에 반영

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니를 찾을 수 없습니다."));

        deliverCartItem(member, cart); // 배송 entity 최신화

        clearCart(cart); // 장바구니 상품 deleteAll
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

        Address storeLoc = addressRepository.findStoreLocation();
        Address userLoc = addressRepository.findLoginUserLocationByEmail(member.getEmail());

        Directions5RequestDto directions5RequestDto = Directions5RequestDto.builder()
                .startLatitude(storeLoc.getLatitude())
                .startLongitude(storeLoc.getLongitude())
                .goalLatitude(userLoc.getLatitude())
                .goalLongitude(userLoc.getLongitude())
                .build();

        long duration = naverApiDirection5.calculateDurationValAsTesting(directions5RequestDto);

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
     * VIP일 시 혜택가 적용하기
     *
     * @param paymentDto
     * @param member
     * @return
     */
    private double ifVIPGetDiscountPrice(PaymentDto paymentDto, Member member) {

        double discountPrice = vipDiscountPolicy.apply(paymentDto.getTotalUsePoint(), member.getRole());

        if(member.getPoint() < discountPrice){
            throw new OutOfPointException("포인트가 부족합니다.");
        }
        return discountPrice;
    }

    /**
     * 장바구니 상품 목록 비우기
     *
     * @param cart
     */
    private void clearCart(Cart cart) {

        // 상품 재고 및 구매수량 변경정보 반영
        for (CartItem cartItem : cart.getCartItemList()) {
            Item item = cartItem.getItem();
            item.decreaseStock(cartItem.getCount());
            item.addPurchaseCount(cartItem.getCount());
            itemRepository.save(item);
        }

        // 장바구니 상품 deleteAll
        cartItemRepository.deleteAll(cart.getCartItemList());

    }

}