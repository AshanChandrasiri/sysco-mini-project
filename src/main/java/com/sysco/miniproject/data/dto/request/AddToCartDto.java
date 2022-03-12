package com.sysco.miniproject.data.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddToCartDto {

    @NotNull
    private Long cartId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(value = 1)
    private int quantity;

}
