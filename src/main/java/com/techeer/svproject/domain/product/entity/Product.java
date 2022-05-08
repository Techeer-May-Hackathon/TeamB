package com.techeer.svproject.domain.product.entity;

import com.techeer.svproject.domain.order.entity.Order;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "productlist")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 16)
    private UUID id;

    @Setter
    @ManyToOne
    private Order order;

    /**상품 가격**/
    @Column(length = 15)
    private int price;

    /**상품 이름**/
    @Column(length = 50)
    private String productName;


    public void update(int price, String productName){
        this.price = price;
        this.productName = productName;
    }

    @Builder
    public Product(
            Order order,
            int price,
            String productName) {
        this.order = order;
        this.price = price;
        this.productName = productName;
    }

}
