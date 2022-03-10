package com.sysco.miniproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysco.miniproject.data.dao.Category;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dto.request.CreateProductDto;
import com.sysco.miniproject.respository.CategoryRepository;
import com.sysco.miniproject.respository.ProductRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

        verify(productRepository).findByCategoryIdAndNameContainingIgnoreCase(1L, "ice");
    }


    @Test
    void createProduct() throws Exception {
        CreateProductDto req = new CreateProductDto(2L, "abs", 2.0, "");

        given(categoryRepository.findById(2L))
                .willReturn(Optional.empty());

        this.mvc.perform(post("/api/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }
}