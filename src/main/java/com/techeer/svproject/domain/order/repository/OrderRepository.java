package com.techeer.svproject.domain.order.repository;

import com.techeer.svproject.domain.order.entity.Order;

import com.techeer.svproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUserId(UUID userId);
}
