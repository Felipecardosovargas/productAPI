package com.teach.productAPI.controller;

import com.teach.productAPI.dto.ProductCreateUpdateDTO;
import com.teach.productAPI.model.Product;
import com.teach.productAPI.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> list() {
        return productService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Product add(@RequestBody ProductCreateUpdateDTO dto) {
        Product newProduct = new Product();
        newProduct.setName(dto.getName());
        newProduct.setPrice(dto.getPrice());
        return productService.add(newProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductCreateUpdateDTO dto) {
        Product updatedProduct = productService.parcialUpdate(id, dto.getName(), dto.getPrice());
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
