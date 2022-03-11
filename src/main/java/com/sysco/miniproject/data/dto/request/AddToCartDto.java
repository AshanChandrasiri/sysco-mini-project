package com.sysco.miniproject.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartDto {

    private Long cartId;

    private Long productId;

    private int quantity;

}
