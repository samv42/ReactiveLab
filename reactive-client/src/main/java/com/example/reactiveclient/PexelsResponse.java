package com.example.reactiveclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PexelsResponse {
    @JsonProperty("total_results")
    int totalResults;
    int page;
    @JsonProperty("per_page")
    int perPage;
    List<PexelPhoto> photos;
}
