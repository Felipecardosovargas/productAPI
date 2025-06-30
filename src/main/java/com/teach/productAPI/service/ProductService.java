package com.teach.productAPI.service;

import com.teach.productAPI.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> list() {
        return new ArrayList<>(products.values());
    }

    public Product findById(Long id) {
        return products.get(id);
    }

    public Product add(Product product) {
        Long id = idGenerator.getAndIncrement();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    public Product parcialUpdate(Long id, String name, BigDecimal price) {
        Product existing = products.get(id);

        if (existing != null) {
            if (name != null) existing.setName(name);
            if (price != null) existing.setPrice(price);
        }

        return existing;
    }

    public void delete(Long id){
        products.remove(id);
    }
}
