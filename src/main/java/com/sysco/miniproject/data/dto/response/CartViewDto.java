package com.sysco.miniproject.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartViewDto {

    private Long id;

    private String name;

    private boolean closed;

    private double totalPrice;

    private int totalItems;

    List<CartProductViewDto> products;

}
