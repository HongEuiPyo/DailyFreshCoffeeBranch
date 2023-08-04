package com.example.dailyFreshCoffeeBranch.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class AddressResponseDto {

    private Long id;
    private String latitude;
    private String longitude;
    private String roadAddress;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;


    @QueryProjection
    public AddressResponseDto(Long id, String latitude, String longitude, String roadAddress, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.roadAddress = roadAddress;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

}