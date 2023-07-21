package com.example.dailyFreshCoffeeBranch.entity;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "delivery")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Delivery extends BaseEntity {

    @Id
    @Column(name = "delivery_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Long deliveryTakenTime;

    private String deliveryDepartureRoadLocation;

    private String deliveryDestinationRoadLocation;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "delivery", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<DeliveryItem> deliveryItemList;

}