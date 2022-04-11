package com.sysco.miniproject.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {

    @NotNull
    private Long cartId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(value = 1)
    private int quantity;

}
