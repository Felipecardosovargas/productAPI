package com.teach.productAPI.repository;

import com.teach.productAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //            findBy[createDate]Between
    List<Product> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
