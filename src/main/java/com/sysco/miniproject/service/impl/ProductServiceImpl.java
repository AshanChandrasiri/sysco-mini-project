package com.sysco.miniproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysco.miniproject.data.dao.Category;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dto.request.CreateCategoryDto;
import com.sysco.miniproject.data.dto.request.CretaeProductDto;
import com.sysco.miniproject.data.dto.response.ViewProductDto;
import com.sysco.miniproject.respository.CategoryRepository;
import com.sysco.miniproject.respository.ProductRepository;
import com.sysco.miniproject.service.ProductService;
import com.sysco.miniproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper;

    @Override
    public Category createCategory(CreateCategoryDto req) {

        Category category = new Category();
        category.setName(req.getName());
        return categoryRepository.save(category);
    }

    @Override
    public Product createProduct(CretaeProductDto req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setPrice(req.getPrice());
        product.setImage(req.getImage());
        product.setCategory(getCategoryById(req.getCategoryId()));
        return productRepository.save(product);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ViewProductDto> getAllProductsByCategory(Long categoryId, Pageable pageable) {

        List<ViewProductDto> result = productRepository.findByCategoryId(categoryId, pageable)
                .stream().map(ViewProductDto::new)
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<ViewProductDto> searchProductByName(Long categoryId, String name) {
        List<ViewProductDto> result = productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, name)
                .stream().map(ViewProductDto::new)
                .collect(Collectors.toList());
        return result;
    }


    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Invalid category id"));
    }

}
