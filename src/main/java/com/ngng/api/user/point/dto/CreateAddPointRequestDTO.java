package com.ngng.api.user.point.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddPointRequestDTO {
    @NotNull
    private Long addCost;

    private String type;

}
