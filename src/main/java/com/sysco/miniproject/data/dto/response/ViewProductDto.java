package com.sysco.miniproject.data.dto.response;

import com.sysco.miniproject.data.dao.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewProductDto {

    private Long id;

    private Long categoryId;

    private String name;

    private double price;

    private String image;

    public ViewProductDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.categoryId = product.getCategory().getId();
    }
}
