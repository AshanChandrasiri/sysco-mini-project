package com.sysco.miniproject.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    private Long id;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long manufacturerId;

    @NotBlank
    private String name;

    @NotNull
    private double price;

    @NotBlank
    private String image;


    public CreateProductDto(Long categoryId, Long manufacturerId, String name, double price, String image) {
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
