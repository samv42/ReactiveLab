package com.example.reactiveclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Photo implements PhotoInterface{
    String id;
    @JsonProperty("created_at")
    String createdAt;
    @JsonProperty("updated_at")
    String updatedAt;
    int width;
    int height;
    String color;
    @JsonProperty("blur_hash")
    String blurHash;
    int likes;
    @JsonProperty("liked_by_user")
    String likedByUser;
    String description;

    Url urls;

    @Override
    public String getPhotoUrl() {
        return urls.thumb;
    }

    @Override
    public String getSource() {
        return "Unsplash";
    }
}
