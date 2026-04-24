package com.example.minijira.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class AIService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String url;

    public AIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String askGPT(String prompt) {

        Map<String, Object> request = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", prompt)
                ))
            )
        );

        String response = webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
                //Send only the text part of the response back to the client

        return response;
    }
}