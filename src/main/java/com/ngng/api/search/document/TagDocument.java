package com.ngng.api.search.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagDocument {

    @Field(type = FieldType.Keyword, analyzer = "nori")
    private String Tag;
}
