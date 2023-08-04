package com.example.dailyFreshCoffeeBranch.domain;

import com.example.dailyFreshCoffeeBranch.constant.DeliveryItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "delivery_item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DeliveryItem extends BaseEntity {

    @Id
    @Column(name = "delivery_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryItemStatus deliveryItemStatus;

    @ColumnDefault("0")
    private int count;

    @OneToOne
    private Item item;

    @ManyToOne
    private Delivery delivery;

}