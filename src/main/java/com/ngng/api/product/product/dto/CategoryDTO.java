package com.ngng.api.product.product.dto;

import com.ngng.api.product.product.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(Category category){
        this.id = category.getCategoryId();
        this.name = category.getCategoryName();
    }

}
