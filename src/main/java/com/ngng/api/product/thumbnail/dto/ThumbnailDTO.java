package com.ngng.api.product.thumbnail.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
