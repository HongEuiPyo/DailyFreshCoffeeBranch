package com.example.dailyFreshCoffeeBranch.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
@ToString
public class Directions5RequestDto {

    private String startLatitude;
    private String startLongitude;
    private String startLocationName;
    private String goalLatitude;
    private String goalLongitude;
    private String goalLocationName;
    private String option;

    public String getStart() {
        return startLongitude + "," + startLatitude;
    }

    public String getGoal() {
        return goalLongitude + "," + goalLatitude;
    }

}