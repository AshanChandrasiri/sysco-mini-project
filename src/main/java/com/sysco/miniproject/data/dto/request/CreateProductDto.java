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

    @NotNull
    private Long categoryId;

    @NotBlank
    private String name;

    @NotNull
    private double price;

    @NotBlank
    private String image;

    @NotBlank
    private String unit;

    @NotBlank
    private String producer;

    @NotBlank
    private String producerImage;

    private String description;
}
