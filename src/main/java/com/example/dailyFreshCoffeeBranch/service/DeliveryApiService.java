package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import com.example.dailyFreshCoffeeBranch.domain.*;
import com.example.dailyFreshCoffeeBranch.dto.AddressResponseDto;
import com.example.dailyFreshCoffeeBranch.dto.NaverApiRequestDto;
import com.example.dailyFreshCoffeeBranch.dto.PaymentFormDto;
import com.example.dailyFreshCoffeeBranch.exception.AddressNotFoundException;
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
    private final SmallBusinessPeopleDiscountPolicyService smallBusinessPeopleDiscountPolicyService;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryItemRepository deliveryItemRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final NaverApiService naverApiService;


    /**
     * 장바구니 상품 배송 확정
     *
     * @param email
     * @param paymentFormDto
     */
    @Transactional
    public void deliverCartItems(String email, PaymentFormDto paymentFormDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("회원을 조회할 수 없습니다."));

        pay(paymentFormDto, member); // 결제정보 회원 entity에 반영

        Cart cart = cartRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니를 찾을 수 없습니다."));

        deliverCartItem(member, cart); // 배송 entity 최신화

        clearCart(cart); // 장바구니 상품 deleteAll
    }

    /**
     * 결제하기
     *
     * @param paymentFormDto
     * @param member
     */
    private void pay(PaymentFormDto paymentFormDto, Member member) {
        double discountPrice = ifVIPGetDiscountPrice(paymentFormDto, member);
        member.payPoint(discountPrice);

        if(discountPrice != paymentFormDto.getTotalUsePoint()){
            paymentFormDto.setDiscount(smallBusinessPeopleDiscountPolicyService.getDiscountRate());
        }
        Payment payment = paymentFormDto.toEntity(member);
        paymentRepository.save(payment);
    }

    /**
     * 모든 장바구니 상품 배송하기
     *
     * @param member
     * @param cart
     */
    private void deliverCartItem(Member member, Cart cart) {

        AddressResponseDto storeLoc = addressRepository.findStoreLocation()
                .orElseThrow(() -> new AddressNotFoundException("매장 주소를 찾을 수 없습니다. 관리자에게 문의하세요."));
        AddressResponseDto userLoc = addressRepository.findLoginUserLocationByEmail(member.getEmail())
                .orElseThrow(() -> new AddressNotFoundException("주소가 등록되어 있지 않습니다. 주소를 등록하시고 다시 배송 신청을 진행해주세요."));

        NaverApiRequestDto naverApiRequestDto = NaverApiRequestDto.builder()
                .startLatitude(storeLoc.getLatitude())
                .startLongitude(storeLoc.getLongitude())
                .goalLatitude(userLoc.getLatitude())
                .goalLongitude(userLoc.getLongitude())
                .build();

        long duration = naverApiService.getDurationThruDirect5Api(naverApiRequestDto);

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
     * @param paymentFormDto
     * @param member
     * @return
     */
    private double ifVIPGetDiscountPrice(PaymentFormDto paymentFormDto, Member member) {

        double discountPrice = smallBusinessPeopleDiscountPolicyService.apply(paymentFormDto.getTotalUsePoint(), member.getRole());

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

            if (item.getStock() == 0) {
                itemRepository.getSoldOut(item.getId());
            }

            itemRepository.save(item);
        }

        // 장바구니 상품 deleteAll
        cartItemRepository.deleteAll(cart.getCartItemList());

    }

}