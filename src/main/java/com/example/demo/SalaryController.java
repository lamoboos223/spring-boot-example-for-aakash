package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class SalaryController {
    @PostMapping(value = "/salary", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String validateSalary(@RequestBody SalaryModel request) {

        WebClient webClient = WebClient.builder()
//                .baseUrl("http://localhost:8181/v1/data/httpapi/authz")
                .baseUrl("https://webhook.site/77742bbf-0460-4b91-84f8-a2a6abccaf2c")
                .build();
        webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> { // Handle errors here, log, retry, etc.
                    return Mono.just("An error occurred.");
                })
                .map(response -> {
                    if (response.contains("\"allow\":true")) {
                        return "You are authorized to view the salary of the employee";
                    } else {
                        return "You are not authorized to view the salary of the employee";
                    }
                })
                .subscribe();
        return "error";
    }
}