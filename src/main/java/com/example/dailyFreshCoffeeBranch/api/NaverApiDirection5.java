package com.example.dailyFreshCoffeeBranch.api;

import com.example.dailyFreshCoffeeBranch.dto.Directions5RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class NaverApiDirection5 {

    private final NaverClient naverClient;

    /**
     * Direction5 API - 소요시간 조회
     *
     * @param requestDto
     * @return
     */
    public long calculateDurationValAsTesting(Directions5RequestDto requestDto) {

        WebClient webClient =
                WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving")
                .build();

        Map<String, Map<String, List<Map<String, Map<String, Object>>>>> response =
                webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("start", requestDto.getStart())
                        .queryParam("goal", requestDto.getGoal())
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", naverClient.getCLIENT_ID())
                .header("X-NCP-APIGW-API-KEY", naverClient.getCLIENT_SECRET())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Object duration =
                response.get("route").get("traoptimal").stream()
                .filter(m -> m.containsKey("summary"))
                .findFirst().get()
                .get("summary")
                .get("duration");

        return Long.parseLong(String.valueOf(duration));
    }

}