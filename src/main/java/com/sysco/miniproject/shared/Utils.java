package com.sysco.miniproject.shared;

import com.sysco.miniproject.data.dao.CartProduct;

import java.util.List;

public class Utils {

    private Utils(){}

    public static double calculateTotal(List<CartProduct> cartProducts) {
        return cartProducts.stream().map(cp -> cp.getQuantity() * cp.getProduct().getPrice())
                .reduce((p1, p2) -> p1 + p2).orElse(0.0);
    }

}
