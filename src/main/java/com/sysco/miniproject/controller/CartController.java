package com.sysco.miniproject.controller;

import com.sysco.miniproject.data.dao.Cart;
import com.sysco.miniproject.data.dao.CartProduct;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dto.request.AddToCartDto;
import com.sysco.miniproject.data.dto.request.CreateCartDto;
import com.sysco.miniproject.data.dto.request.CreateProductDto;
import com.sysco.miniproject.data.dto.response.CartViewDto;
import com.sysco.miniproject.respository.models.CartDetails;
import com.sysco.miniproject.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {


    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@Valid @RequestBody CreateCartDto req) {
        log.info("request to create cart, {}", req);
        Cart result = cartService.createCart(req);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<CartProduct> addToCard(@Valid @RequestBody AddToCartDto req) {
        log.info("request to add to cart, {}", req);
        CartProduct result = cartService.addToCard(req);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<CartProduct> remove(@PathVariable Long cartId, @PathVariable Long productId) {
        log.info("request to remove from cart");
        cartService.removeFromCart(cartId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/view/{cartId}")
    public ResponseEntity<CartViewDto> view(@PathVariable Long cartId) {
        log.info("request to view to cart, {}", cartId);
        CartViewDto result = cartService.viewCart(cartId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/user-carts")
    public ResponseEntity<List<CartDetails>> getUsersCarts() {
        log.info("request to get users carts");
        List<CartDetails> result = cartService.getUserCarts();
        return ResponseEntity.ok().body(result);
    }
}
