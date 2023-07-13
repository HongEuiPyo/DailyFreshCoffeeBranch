package com.example.dailyFreshCoffeeBranch.entity;

import com.example.dailyFreshCoffeeBranch.dto.ImageFileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image_file")
@NoArgsConstructor
@Getter
public class ImageFile {

    @Id
    @Column(name = "image_file_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageName;
    private String originalImageName;
    private String imageUrl;
    private String savedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ImageFile(String imageName, String originalImageName, String imageUrl, Item item, String savedFileName) {
        this.imageName = imageName;
        this.originalImageName = originalImageName;
        this.imageUrl = imageUrl;
        this.item = item;
        this.savedFileName = savedFileName;
    }

    public ImageFileDto toDto() {
        return ImageFileDto.builder()
                .id(id)
                .imageName(imageName)
                .originalImageName(originalImageName)
                .imageUrl(imageUrl)
                .itemId(item.getId())
                .savedFileName(savedFileName)
                .build();
    }

    // 이미지 파일 수정
    public void update(String originalImageName, String imageName, String imageUrl) {
        this.originalImageName = originalImageName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

}