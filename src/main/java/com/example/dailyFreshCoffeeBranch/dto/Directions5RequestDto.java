package com.example.dailyFreshCoffeeBranch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Directions5RequestDto {

    private double startLatitude;
    private double startLongitude;
    private double goalLatitude;
    private double goalLongitude;
    private String option;

    public String getStart() {
        return startLatitude + "," + startLongitude;
    }

    public String getGoal() {
        return goalLatitude + "," + goalLongitude;
    }

}