package com.teach.productAPI.service;

import com.teach.productAPI.dto.ProductMapper;
import com.teach.productAPI.dto.ProductRequestDTO;
import com.teach.productAPI.dto.ProductResponseDTO;
import com.teach.productAPI.exeptions.ResourceNotFoundException;
import com.teach.productAPI.model.Product;
import com.teach.productAPI.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllProducts(LocalDateTime startDate, LocalDateTime endDate) {
        List<Product> products;

        if (startDate != null && endDate != null) {
            products = productRepository.findByCreateDateBetween(startDate, endDate);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID "+id));
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        product.setCreateDate(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, @Valid ProductResponseDTO productRequestDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: "));

        if (productRequestDTO.getName() != null && !productRequestDTO.getName().isBlank()) {
            existingProduct.setName(productRequestDTO.getName());
        }

        if (productRequestDTO.getPrice() != null) {
            existingProduct.setPrice(productRequestDTO.getPrice());
        }

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: "+ id);
        }

        productRepository.deleteById(id);
    }
}
