package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.dto.Directions5RequestDto;
import com.example.dailyFreshCoffeeBranch.dto.GeocodingRequestDto;
import com.example.dailyFreshCoffeeBranch.dto.ReverseGeocodingRequestDto;
import com.example.dailyFreshCoffeeBranch.dto.StaticMapRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@RequestMapping("/api/naver/map")
@RestController
public class NaverMapApiController {

    private final NaverClient naverClient;


    /**
     * 지도 반환
     * @param requestDto
     * @return
     */
    @GetMapping("/staticMap")
    public Mono<byte[]> staticMap(@RequestBody StaticMapRequestDto requestDto) {

        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8080/api/naver/map/staticMap")
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
                .bodyToMono(byte[].class);
    }

    /**
     * 좌표 주소 반환
     * @param requestDto
     * @return
     */
    @GetMapping("/geocoding")
    public Mono<String> geocoding(@RequestBody GeocodingRequestDto requestDto) {

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
                .bodyToMono(String.class);
    }

    /**
     * 도로명 주소 반환
     * @param requestDto
     * @return
     */
    @GetMapping("/reverseGeocoding")
    public Mono<String> reverseGeocoding(@RequestBody ReverseGeocodingRequestDto requestDto) {

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
                .bodyToMono(String.class);
    }

    /**
     * 최단거리, 거리 소요시간 반환
     * @param requestDto
     * @return
     */
    @GetMapping("/naver/map/directions5")
    public Mono<String> directions5(@RequestBody Directions5RequestDto requestDto) {

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
                .bodyToMono(String.class);
    }

}