package com.sysco.miniproject.controller;

import com.sysco.miniproject.data.dao.Category;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.data.dto.request.CreateCategoryDto;
import com.sysco.miniproject.data.dto.request.CreateUserDto;
import com.sysco.miniproject.data.dto.request.CretaeProductDto;
import com.sysco.miniproject.data.dto.response.ViewProductDto;
import com.sysco.miniproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CretaeProductDto req) {
        log.info("request to create product, {}", req);
        Product result = productService.createProduct(req);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryDto req) {
        log.info("request to create category, {}", req);
        Category result = productService.createCategory(req);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/product/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("request to get all categories");
        return ResponseEntity.ok().body(productService.getAllCategories());
    }

    @GetMapping("/product/all/{categoryId}")
    public ResponseEntity<List<ViewProductDto>> getAllProductsByCategory(@PathVariable Long categoryId, Pageable pageable) {
        log.info("request to get all categories");
        return ResponseEntity.ok().body(productService.getAllProductsByCategory(categoryId, pageable));
    }

    @GetMapping("/product/search/{categoryId}/{name}")
    public ResponseEntity<List<ViewProductDto>> searchProductByName( @PathVariable Long categoryId, @PathVariable String name) {
        log.info("request to get all categories");
        return ResponseEntity.ok().body(productService.searchProductByName(categoryId, name));
    }
}
