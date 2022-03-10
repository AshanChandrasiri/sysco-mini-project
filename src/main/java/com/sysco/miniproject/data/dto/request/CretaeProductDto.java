package com.sysco.miniproject.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CretaeProductDto {

    @NotNull
    private Long categoryId;

    @NotBlank
    private String name;

    @NotNull
    private double price;

    private String image;
}
