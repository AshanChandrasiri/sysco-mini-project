package com.sysco.miniproject.service;

import com.sysco.miniproject.data.dao.Cart;
import com.sysco.miniproject.data.dao.CartProduct;
import com.sysco.miniproject.data.dto.request.AddToCartDto;
import com.sysco.miniproject.data.dto.request.CreateCartDto;
import com.sysco.miniproject.data.dto.response.CartViewDto;

public interface CartService {

    Cart createCart(CreateCartDto dto);

    CartProduct addToCard(AddToCartDto req);

    void removeFromCart(Long cartId, Long productId);

    CartViewDto viewCart(Long cartId);
}
