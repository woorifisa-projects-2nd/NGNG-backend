package com.ngng.api.thumbnail.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@Builder
public class ThumbnailDTO {
    Long id;
    String thumbnailURL;

    public ThumbnailDTO(Long id, String thumbnailURL){
        this.id = id;
        this.thumbnailURL = thumbnailURL;
    }
}
