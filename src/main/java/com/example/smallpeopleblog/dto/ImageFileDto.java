package com.example.smallpeopleblog.dto;

import com.example.smallpeopleblog.entity.ImageFile;
import com.example.smallpeopleblog.entity.Item;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ImageFileDto {

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
