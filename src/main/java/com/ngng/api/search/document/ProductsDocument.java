package com.ngng.api.search.document;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Document(indexName = "products")
public class ProductsDocument {

    @Id
    private String id;

    @Field(type = FieldType.Long, name = "product_id")
    private Long productId;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String title;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String content;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String category;

    @Field(type = FieldType.Text, name = "thumbnail_url")
    private String thumbnailUrl;

    @Field(type = FieldType.Date, name = "created_at")
    private Date createdAt;

    @Field(type = FieldType.Date, name = "updated_at")
    private Date updatedAt;

    @Field(type = FieldType.Date, name = "refreshed_at")
    private Date refreshedAt;

    @Field(type = FieldType.Boolean)
    private boolean discountable;

    @Field(type = FieldType.Boolean, name = "for_sale")
    private boolean forSale;

    @Field(type = FieldType.Boolean, name = "is_escrow")
    private boolean isEscrow;

    @Field(type = FieldType.Long)
    private Long price;

    @Field(type = FieldType.Object)
    private List<String> tags = new ArrayList<>();
}
