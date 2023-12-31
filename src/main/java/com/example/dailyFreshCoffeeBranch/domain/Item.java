package com.example.dailyFreshCoffeeBranch.domain;

import com.example.dailyFreshCoffeeBranch.constant.ItemCategory;
import com.example.dailyFreshCoffeeBranch.constant.ItemStatus;
import com.example.dailyFreshCoffeeBranch.dto.ImageFileFormDto;
import com.example.dailyFreshCoffeeBranch.dto.ItemFormDto;
import com.example.dailyFreshCoffeeBranch.exception.OutOfStockException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String summary;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    @Column(nullable = false)
    private int price;

    @ColumnDefault("0")
    private int stock;

    @ColumnDefault("0")
    private int purchaseCnt;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    List<ImageFile> imageFileList;


    public ItemFormDto toDto() {
        List<ImageFileFormDto> imageFileFormDtoList = new ArrayList<>();
        if (imageFileList!=null) {
            imageFileFormDtoList = imageFileList.stream()
                    .map(imageFile -> imageFile.toDto())
                    .collect(Collectors.toList());
        }
        return ItemFormDto.builder()
                .id(id)
                .title(title)
                .price(price)
                .summary(summary)
                .stock(stock)
                .itemStatus(itemStatus)
                .itemCategory(itemCategory)
                .imageFileFormDtoList(imageFileFormDtoList)
                .purchaseCnt(purchaseCnt)
                .createdTime(getCreatedTime())
                .modifiedTime(getModifiedTime())
                .build();
    }

    // 상품정보 변경
    public void update(ItemFormDto dto) {
        this.title = dto.getTitle();
        this.summary = dto.getSummary();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
    }

    // 재고수량 감소
    public void decreaseStock(int purchaseQuantity) {
        int restStock = this.stock - purchaseQuantity;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stock + ")");
        }
        this.stock = restStock;
    }

    // 재고수량 추가
    public void increaseStock(int stock) {
        this.stock += stock;
    }

    // 상품 상태 변경
    public void updateItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    // 상품 구매수량 증가
    public void addPurchaseCount(int count) {
        purchaseCnt += count;
    }

}