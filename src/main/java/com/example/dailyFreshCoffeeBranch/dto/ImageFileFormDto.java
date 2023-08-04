package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.domain.ImageFile;
import com.example.dailyFreshCoffeeBranch.domain.Item;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ImageFileFormDto {

    private Long id;
    private String imageName;
    private String originalImageName;
    private String imageUrl;
    private Long itemId;
    private String savedFileName;

    public ImageFile toEntity(Item item) {
        return ImageFile.builder()
                .imageName(imageName)
                .originalImageName(originalImageName)
                .imageUrl(imageUrl)
                .item(item)
                .savedFileName(savedFileName)
                .build();
    }

}
