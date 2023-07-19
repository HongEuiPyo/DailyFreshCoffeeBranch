package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.entity.Address;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class AddressDto {

    private String latitude;

    private String longitude;

    private String roadAddress;

    public static AddressDto of(Address address) {
        return AddressDto.builder()
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .roadAddress(address.getRoadAddress())
                .build();
    }
}
