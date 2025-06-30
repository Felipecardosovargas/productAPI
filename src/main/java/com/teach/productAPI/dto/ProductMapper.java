package com.teach.productAPI.dto;

import com.teach.productAPI.model.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto) {
        if (dto ==  null) {
            return null;
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCreateDate(LocalDateTime.now());
        return product;
    }

    public ProductResponseDTO toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCreateDate(product.getCreateDate());
        return dto;
    }
}
