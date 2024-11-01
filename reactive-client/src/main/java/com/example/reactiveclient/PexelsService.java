package com.example.reactiveclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PexelsService {
    @Autowired
    @Qualifier("pexelsWebClient")
    WebClient webClient;

    public Flux<PexelPhoto> getPhotos(String searchText) {
        return getTotalPages(searchText)
                .flatMapMany(t -> Flux.range(1, t > 5 ? 5 : t))
                .flatMap(f -> searchPexels(searchText, f)
                        .flatMapIterable(PexelsResponse::getPhotos), 5);
    }

    public Mono<Integer> getTotalPages(String searchText) {
        return webClient.get()
                .uri(uri -> uri
                        .queryParam("page", "1")
                        .queryParam("query", searchText)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(PexelsResponse.class)
                .map(PexelsResponse::getTotalResults)
                .map(Integer::valueOf);
    }

    public Mono<PexelsResponse> searchPexels(String searchText, int pageNumber) {
        return webClient.get()
                .uri(uri -> uri
                        .queryParam("page", pageNumber)
                        .queryParam("query", searchText)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PexelsResponse.class);
    }
}
