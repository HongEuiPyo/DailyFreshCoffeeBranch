package com.example.dailyFreshCoffeeBranch.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class StaticMapRequestDto {

    private int width;
    private int height;
    private double latitude;
    private double longitude;
    private int level;

    public String getCenter() {
        return latitude + "," + longitude;
    }

}