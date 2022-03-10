package com.sysco.miniproject.controller;

import com.sysco.miniproject.data.dao.Category;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.respository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void searchProductByName() throws Exception {

        Category category1 = new Category(1L, "ice-cream");
        Product product1 = new Product(1L, "chocolate ice cream", 2, "", category1);
        Product product2 = new Product(2L, "strawberry ice cream", 3, "", category1);

        given(productRepository.findByCategoryIdAndNameContainingIgnoreCase(1L, "ice"))
                .willReturn(Arrays.asList(product1, product2));

        this.mvc.perform(get("/api/product/search/1/ice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // 2 items in the response
                .andExpect(jsonPath("$[0].*", hasSize(5)) // 1 object in thr result list has 5 fields
                );
    }
}