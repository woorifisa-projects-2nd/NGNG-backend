package com.ngng.api.thumbnail.dto;

public class ThumbnailDTO {
    Long id;
    String thumbnailURL;

    public ThumbnailDTO(Long id, String thumbnailURL){
        this.id = id;
        this.thumbnailURL = thumbnailURL;
    }
}
