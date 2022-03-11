package com.sysco.miniproject.data.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductViewDto {

    private Long id;

    private String name;

    private double unitPrice;

    private String image;

    private int quantity;

}
