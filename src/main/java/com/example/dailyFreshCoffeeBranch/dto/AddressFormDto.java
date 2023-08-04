package com.example.dailyFreshCoffeeBranch.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class AddressFormDto {

    private Long id;
    private String latitude;
    private String longitude;
    private String roadAddress;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

}