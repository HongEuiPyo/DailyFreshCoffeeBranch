package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.dto.Directions5RequestDto;
import com.example.dailyFreshCoffeeBranch.dto.GeocodingRequestDto;
import com.example.dailyFreshCoffeeBranch.dto.ReverseGeocodingRequestDto;
import com.example.dailyFreshCoffeeBranch.dto.StaticMapRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class NaverMapApi {

    private final NaverClient naverClient;


    public byte[] staticMap(StaticMapRequestDto requestDto) {

        WebClient client = WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-static/v2/raster")
                .build();

        return client.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("w", requestDto.getWidth())
                    .queryParam("h", requestDto.getHeight())
                    .queryParam("center", requestDto.getCenter())
                    .queryParam("level", requestDto.getLevel())
                    .build())
                .header("X-NCP-APIGW-API-KEY-ID", naverClient.getCLIENT_ID())
                .header("X-NCP-APIGW-API-KEY", naverClient.getCLIENT_SECRET())
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    public String geocoding(GeocodingRequestDto requestDto) {

        WebClient client =  WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
                .build();

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", requestDto.getQuery())
                        .queryParam("coordinate", requestDto.getCoordinate())
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", naverClient.getCLIENT_ID())
                .header("X-NCP-APIGW-API-KEY", naverClient.getCLIENT_SECRET())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String reverseGeocoding(ReverseGeocodingRequestDto requestDto) {

        WebClient client =  WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc")
                .build();

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("request", requestDto.getRequest())
                        .queryParam("coords", requestDto.getCoords())
                        .queryParam("sourcecrs", requestDto.getSourcecrs())
                        .queryParam("output", requestDto.getOutput())
                        .queryParam("orders", requestDto.getOrders())
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", naverClient.getCLIENT_ID())
                .header("X-NCP-APIGW-API-KEY", naverClient.getCLIENT_SECRET())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String directions5(Directions5RequestDto requestDto) {

        WebClient client =  WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving")
                .build();

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("start", requestDto.getStart())
                        .queryParam("goal", requestDto.getGoal())
                        .queryParam("option", requestDto.getOption())
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", naverClient.getCLIENT_ID())
                .header("X-NCP-APIGW-API-KEY", naverClient.getCLIENT_SECRET())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}