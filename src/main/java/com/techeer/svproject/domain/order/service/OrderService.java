package com.techeer.svproject.domain.order.service;

import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.repository.OrderRepository;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order save(Order entity, User user) {
        Order order = entity;
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order findById(UUID id) {
        return orderRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Order> findAllByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return orderRepository.findAllByUserId(user.getId());
    }

    @Transactional
    public void delete(UUID id) {
        Order order = orderRepository.findById(id).get();
//                .orElseThrow(
//                        () -> new ("Developer profile not found with id=" + id));
        orderRepository.delete(order);   }
}