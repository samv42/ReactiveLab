package com.example.reactiveclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class UnsplashResponse {
    int total;
    @JsonProperty("total_pages")
    int totalPages;
    List<Photo> results;

}
