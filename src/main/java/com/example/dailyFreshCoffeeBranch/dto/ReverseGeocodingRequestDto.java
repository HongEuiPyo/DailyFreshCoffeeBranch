package com.example.dailyFreshCoffeeBranch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ReverseGeocodingRequestDto {

    private String request;
    private double latitude;
    private double longitude;
    private String sourcecrs;
    private String output;
    private String orders;

    public String getCoords() {
        return latitude + "," + longitude;
    }

}