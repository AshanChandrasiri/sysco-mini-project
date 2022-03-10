package com.sysco.miniproject.service;

import com.sysco.miniproject.data.dao.Category;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dto.request.CreateCategoryDto;
import com.sysco.miniproject.data.dto.request.CreateProductDto;
import com.sysco.miniproject.data.dto.response.ViewProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Category createCategory(CreateCategoryDto req);

    Product createProduct(CreateProductDto req);

    List<Category> getAllCategories();

    List<ViewProductDto> getAllProductsByCategory(Long categoryId, Pageable pageable);

    List<ViewProductDto> searchProductByName(Long categoryId, String name);

}
