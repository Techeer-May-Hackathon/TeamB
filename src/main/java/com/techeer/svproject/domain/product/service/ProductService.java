package com.techeer.svproject.domain.product.service;

import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.repository.OrderRepository;
import com.techeer.svproject.domain.product.dto.ProductResponseDto;
import com.techeer.svproject.domain.product.dto.ProductSaveDto;
import com.techeer.svproject.domain.product.dto.ProductUpdateDto;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Product save(ProductSaveDto productSaveDto){
        Product product = productSaveDto.toEntity();
        Order order = orderRepository.findById(productSaveDto.getOrderId()).get();
        product.setOrder(order);
        return productRepository.save(product);
    }

    @Transactional
    public Product update(UUID id, ProductUpdateDto productUpdateDto){
        Product product = productRepository.findById(id).get();
        product.update(productUpdateDto.getPrice(), productUpdateDto.getProductName());
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(UUID id){
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllByOrderId(UUID id) {
        return productRepository.findByOrderId(id).stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID id){
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }
}
