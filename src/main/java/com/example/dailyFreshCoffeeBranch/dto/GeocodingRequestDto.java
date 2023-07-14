package com.example.dailyFreshCoffeeBranch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class GeocodingRequestDto {

    private String query;
    private double latitude;
    private double longitude;

    public String getCoordinate() {
        return latitude + "," + longitude;
    }

}