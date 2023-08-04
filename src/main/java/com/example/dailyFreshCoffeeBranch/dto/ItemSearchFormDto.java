package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.ItemCategory;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ItemSearchFormDto {

    private ItemCategory searchCategory1 = ItemCategory.ALL;
    private String searchCategory2 = "";
    private String searchCategory3 = "";
    private String searchType = "";
    private String searchKeyword = "";

}
