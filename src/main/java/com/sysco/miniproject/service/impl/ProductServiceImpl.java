package com.sysco.miniproject.service.impl;

import com.sysco.miniproject.data.dao.Category;
import com.sysco.miniproject.data.dao.Manufacturer;
import com.sysco.miniproject.data.dao.Product;
import com.sysco.miniproject.data.dto.request.CreateCategoryDto;
import com.sysco.miniproject.data.dto.request.CreateProductDto;
import com.sysco.miniproject.data.dto.response.ViewProductDto;
import com.sysco.miniproject.respository.CategoryRepository;
import com.sysco.miniproject.respository.ProductRepository;
import com.sysco.miniproject.service.ManufacturerService;
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

    private final ManufacturerService manufacturerService;


    @Override
    public Category createCategory(CreateCategoryDto req) {

        Category category = new Category();
        if (req.getId() != null) {
            category = getCategoryById(req.getId());
        }
        category.setName(req.getName());
        category.setImageUrl(req.getImage());
        return categoryRepository.save(category);
    }

    @Override
    public Product createProduct(CreateProductDto req) {

        Product product = new Product();
        if (req.getId() != null) {
            product = getProduct(req.getId());
        }

        product.setCategory(getCategoryById(req.getCategoryId()));

        Manufacturer manufacturer = manufacturerService.getManufactureById(req.getManufacturerId());
        product.setManufacturer(manufacturer);

        product.setName(req.getName());
        product.setPrice(req.getPrice());
        product.setImage(req.getImage());

        product.setUnit("$");

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return getProduct(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ViewProductDto> getAllProductsByCategory(Long categoryId, Pageable pageable) {

        return productRepository.findByCategoryId(categoryId, pageable)
                .stream().map(ViewProductDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewProductDto> searchProductByName(Long categoryId, String name, Pageable pageable) {
        return productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, name, pageable)
                .stream().map(ViewProductDto::new)
                .collect(Collectors.toList());

    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Invalid category id"));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Invalid Product id"));
    }
}
