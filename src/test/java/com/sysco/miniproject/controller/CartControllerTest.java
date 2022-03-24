package com.sysco.miniproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysco.miniproject.data.dao.Cart;
import com.sysco.miniproject.data.dao.CartProduct;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.data.dto.request.AddToCartDto;
import com.sysco.miniproject.respository.CartProductRepository;
import com.sysco.miniproject.respository.CartRepository;
import com.sysco.miniproject.service.AuthService;
import com.sysco.miniproject.shared.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CartProductRepository cartProductRepository;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void totalCalculatorTest() throws Exception {

        Cart cart = new Cart(1L, "grocery", false, null, null);

        Product product1 = new Product(1l, "p1", 1, "", null, null, null);
        Product product2 = new Product(2l, "p2", 2, "", null, null, null);
        Product product3 = new Product(3l, "p3", 3, "", null, null, null);

        CartProduct cp1 = new CartProduct(1L, cart, product1, 2);
        CartProduct cp2 = new CartProduct(2L, cart, product2, 3);
        CartProduct cp3 = new CartProduct(3L, cart, product3, 1);

        assertEquals(11, Utils.calculateTotal(Arrays.asList(cp1, cp2, cp3)));


    }

    @Test
    void view() throws Exception {

        User user = new User(1l, "User", "user", "user@gmail.com", "", null);

        Cart cart = new Cart(1L, "grocery", false, user, null);

        Product product1 = new Product(1l, "p1", 1, "", null, null, null);
        Product product2 = new Product(2l, "p2", 2, "", null, null, null);
        Product product3 = new Product(3l, "p3", 3, "", null, null, null);

        CartProduct cp1 = new CartProduct(1L, cart, product1, 2);
        CartProduct cp2 = new CartProduct(2L, cart, product2, 3);
        CartProduct cp3 = new CartProduct(3L, cart, product3, 1);

        cart.setCartProducts(Arrays.asList(cp1, cp2, cp3));

        given(authService.getContextUser())
                .willReturn(user);

        given(cartRepository.findByIdAndUserId(cart.getId(), user.getId()))
                .willReturn(Optional.of(cart));

        this.mvc.perform(get("/api/cart/view/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(11))
                .andExpect(jsonPath("$.products", hasSize(3)));

    }

    @Test
    void viewNotUserCart() throws Exception {
        User user = new User(1l, "User", "user", "user@gmail.com", "", null);

        Cart cart = new Cart(1L, "grocery", false, user, null);

        given(authService.getContextUser())
                .willReturn(user);

        given(cartRepository.findByIdAndUserId(cart.getId(), 2L))
                .willReturn(Optional.of(cart));

        this.mvc.perform(get("/api/cart/view/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddToCart() throws Exception {
        User user = new User(1l, "User", "user", "user@gmail.com", "", null);
        given(authService.getContextUser())
                .willReturn(user);


        CartProduct cartProduct = new CartProduct();
        Cart cart = new Cart(1L, "grocery", false, user, null);

        Product product1 = new Product(1l, "p1", 1, "", null, null, null);

        cartProduct.setCart(cart);
        cartProduct.setProduct(product1);
        cartProduct.setQuantity(1);
        cartProduct.setId(1L);

        given(cartProductRepository.findByCartIdAndCartUserIdAndProductId(cart.getId(), user.getId(), product1.getId()))
                .willReturn(Optional.of(cartProduct));

        given(cartProductRepository.save(cartProduct))
                .willReturn(cartProduct);

        AddToCartDto dto = new AddToCartDto();
        dto.setCartId(cart.getId());
        dto.setProductId(product1.getId());
        dto.setQuantity(2);

        this.mvc.perform(post("/api/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(3));


        verify(cartProductRepository).findByCartIdAndCartUserIdAndProductId(cart.getId(), user.getId(), product1.getId());


    }
}