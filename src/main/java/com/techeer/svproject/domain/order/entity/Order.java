package com.techeer.svproject.domain.order.entity;

import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderlist")
public class Order {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;

    @Setter
    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Product> products;

    /** 주문 날짜 */
    @CreationTimestamp
    private LocalDateTime orderDate;

    @Builder
    public Order(
            User user,
            LocalDateTime orderDate) {
        this.user = user;
        this.orderDate = orderDate;
    }
}
