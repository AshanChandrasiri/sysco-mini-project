package com.sysco.miniproject.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateCategoryDto {

    private Long id;

    @NotBlank
    private String name;
}
