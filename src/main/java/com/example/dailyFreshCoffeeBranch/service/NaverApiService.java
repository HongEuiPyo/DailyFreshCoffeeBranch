package com.example.dailyFreshCoffeeBranch.service;

import com.example.dailyFreshCoffeeBranch.dto.NaverApiRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;


@Service
public class NaverApiService {

    @Value("${naver.client-id}")
    private String CLIENT_ID;

    @Value("${naver.client-secret}")
    private String CLIENT_SECRET;


    /**
     * Direction5 API - 소요시간 조회
     *
     * @param requestDto
     * @return
     */
    public long getDurationThruDirect5Api(NaverApiRequestDto requestDto) {

        WebClient webClient = WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving")
                .build();

        Map<String, Map<String, List<Map<String, Map<String, Object>>>>> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("start", requestDto.getStart())
                        .queryParam("goal", requestDto.getGoal())
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", CLIENT_ID)
                .header("X-NCP-APIGW-API-KEY", CLIENT_SECRET)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Object duration = response.get("route").get("traoptimal").stream()
                .filter(m -> m.containsKey("summary"))
                .findFirst().get()
                .get("summary")
                .get("duration");

        return Long.parseLong(String.valueOf(duration));
    }

}