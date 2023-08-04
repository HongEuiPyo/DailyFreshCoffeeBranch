package com.example.dailyFreshCoffeeBranch.domain;

import com.example.dailyFreshCoffeeBranch.dto.PaymentFormDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Payment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    private String itemNames;

    private int totalPayPrice;

    @Column(columnDefinition = "double")
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public PaymentFormDto toDto() {
        return PaymentFormDto.builder()
                .id(id)
                .itemNames(itemNames)
                .totalUsePoint(totalPayPrice)
                .discount(discount)
                .modifiedTime(getModifiedTime())
                .build();
    }
}
