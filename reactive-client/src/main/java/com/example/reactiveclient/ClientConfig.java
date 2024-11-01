package com.example.reactiveclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;



@Configuration
public class ClientConfig {
    @Value("${search.uri}")
    private URI searchUri;

    @Value("${api.client-id}")
    private String secret;

    @Value("${pexelsSeach.uri}")
    private URI pexelsSearchUri;

    @Value("${pexelsApi.client-id}")
    private String secret2;

    @Bean
    @Qualifier("unsplashWebClient")
    public WebClient unsplashWebClient() {
        return WebClient.builder()
                .baseUrl(searchUri.toString())
                .defaultHeader(HttpHeaders.AUTHORIZATION, secret)
                .build();
    }
    @Bean
    //@Qualifier("pexelsWebClient")
    public WebClient pexelsWebClient() {
        return WebClient.builder()
                .baseUrl(pexelsSearchUri.toString())
                .filters(e -> {
                    e.add(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                        System.out.println(clientRequest.toString());
                        return Mono.just(clientRequest);
                    }));
                    e.add(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                        System.out.println(clientResponse.toString());
                        return Mono.just(clientResponse);
                    }));
                })
                .defaultHeader(HttpHeaders.AUTHORIZATION, secret2)
                .build();
    }
}
