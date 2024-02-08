package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class SalaryController {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public SalaryController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8181/v1/data/httpapi/authz")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping(value = "/salary", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> validateSalary(@RequestBody SalaryModel request) {
        String opaPayload = String.format("{" +
                "\"input\": {" +
                "\"user\": \"%s\"," +
                "\"path\": [\"finance\", \"salary\", \"%s\"]," +
                "\"method\": \"POST\"" +
                "}" +
                "}", request.getUsername(), request.getEmployee());

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(opaPayload))
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        // Deserialize the JSON response into a Java object
                        SalaryResponse responseObj = objectMapper.readValue(response, SalaryResponse.class);
                        if (responseObj.getResult().isAllow()) {
                            return "Authorized";
                        }
                        return "Not Authorized";
                    } catch (Exception e) {
                        // Handle any exceptions that occur during deserialization
                        e.printStackTrace();
                        return "Error";
                    }
                });
    }
}