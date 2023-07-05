package com.example.smallpeopleblog.dto;

import com.example.smallpeopleblog.constant.ItemStatus;
import com.example.smallpeopleblog.entity.Item;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private List<ImageFileDto> imageFileDtoList = new ArrayList<>();


    public Item toEntity() {
        return Item.builder()
                .title(this.title)
                .price(this.price)
                .summary(this.summary)
                .stock(this.stock)
                .itemStatus(this.itemStatus)
                .build();
    }

}