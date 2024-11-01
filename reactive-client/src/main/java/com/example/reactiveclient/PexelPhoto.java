package com.example.reactiveclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PexelPhoto  implements PhotoInterface{
   int id;
   int width;
   int height;
   String url;
   String photographer;
    @JsonProperty("photographer_url")
   String photographerUrl;
    @JsonProperty("photographer_id")
    int photographerId;
    @JsonProperty("avg_color")
    String avgColor;
    Source src;
    String alt;

    @Override
    public String getPhotoUrl() {
        return src.small;
    }

    @Override
    public String getSource() {
        return "Pexel";
    }
}
