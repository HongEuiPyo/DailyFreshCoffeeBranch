package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.ItemCategory;
import com.example.dailyFreshCoffeeBranch.constant.ItemStatus;
import com.example.dailyFreshCoffeeBranch.entity.Item;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class ItemDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "설명은 필수 입력 값입니다.")
    private String summary;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stock;

    private ItemStatus itemStatus;

    private ItemCategory itemCategory;

    private List<ImageFileDto> imageFileDtoList = new ArrayList<>();

    private int purchaseCnt;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;


    public Item toEntity() {
        return Item.builder()
                .title(title)
                .price(price)
                .summary(summary)
                .stock(stock)
                .itemStatus(itemStatus)
                .purchaseCnt(purchaseCnt)
                .build();
    }

}