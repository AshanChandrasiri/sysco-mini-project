package com.sysco.miniproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysco.miniproject.data.dao.Cart;
import com.sysco.miniproject.data.dao.Category;
import com.sysco.miniproject.data.dao.Manufacturer;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dto.request.CreateCategoryDto;
import com.sysco.miniproject.data.dto.request.CreateProductDto;
import com.sysco.miniproject.respository.CategoryRepository;
import com.sysco.miniproject.respository.ManufacturerRepository;
import com.sysco.miniproject.respository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockBean
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ObjectMapper objectMapper;


    /*
     * Test update category record which is not available
     * */
    @Test
    void createCategory() throws Exception {

        CreateCategoryDto req = new CreateCategoryDto(1L, "c1", "http//:image.lk");

        given(categoryRepository.findById(1L))
                .willReturn(Optional.empty());


        this.mvc.perform(post("/api/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());

        verify(categoryRepository).findById(1L);
    }

    @Test
    void testCreateProductWhenInvalidCategoryId() throws Exception {
        CreateProductDto req = new CreateProductDto(2l, 1l, "name", 2.0, "http://image.jpg");

//        Category category = new Category(2L, "cart1", null);
        given(categoryRepository.findById(2L))
                .willReturn(Optional.empty());

        this.mvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());

        verify(categoryRepository).findById(2L);
    }


    @Test
    void testCreateProductInvalidManufactureId() throws Exception {

        CreateProductDto req = new CreateProductDto(2l, 1l, "name", 2.0, "http://image.jpg");

        Category category = new Category(2L, "cart1", null);
        given(categoryRepository.findById(2L))
                .willReturn(Optional.of(category));

        given(manufacturerRepository.findById(1l))
                .willReturn(Optional.empty());

        this.mvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());

        verify(manufacturerRepository).findById(1l);
    }


    @Test
    void searchProductByName() throws Exception {

        Pageable pageable = PageRequest.of(0, 20);

        Manufacturer m1 = new Manufacturer(1l, "m1", "", "");
        Manufacturer m2 = new Manufacturer(2l, "m2", "", "");

        Category category1 = new Category(1L, "ice-cream", "");
        Product product1 = new Product(1L, "chocolate ice cream", 2, "", category1, m1, null);
        Product product2 = new Product(2L, "strawberry ice cream", 3, "", category1, m1, null);

        given(productRepository.findByCategoryIdAndNameContainingIgnoreCase(1L, "ice", pageable))
                .willReturn(Arrays.asList(product1, product2));

        this.mvc.perform(get("/api/product/search/1/ice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // 2 items in the response
                .andExpect(jsonPath("$[0].*", hasSize(9)) // 1 object in thr result list has 5 fields
                );

        verify(productRepository).findByCategoryIdAndNameContainingIgnoreCase(1L, "ice", pageable);
    }

    @Test
    void testGetAllProductsByCategory() throws Exception {
        Pageable pageable = PageRequest.of(0, 20);

        Manufacturer m1 = new Manufacturer(1l, "m1", "", "");

        Category category1 = new Category(1L, "ice-cream", "");
        Product product1 = new Product(1L, "chocolate ice cream", 2, "", category1, m1, null);

        given(productRepository.findByCategoryId(1L, pageable))
                .willReturn(Arrays.asList(product1));

        this.mvc.perform(get("/api/product/all/" + category1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // 2 items in the response
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].name").value(product1.getName()))  // 1 object in thr result list has 5 fields
        ;

    }

}