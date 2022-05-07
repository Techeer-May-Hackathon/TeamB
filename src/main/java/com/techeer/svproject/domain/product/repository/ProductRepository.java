package com.techeer.svproject.domain.product.repository;

import com.techeer.svproject.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByOrderId(UUID id);
}
