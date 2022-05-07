package com.techeer.svproject.domain.user;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.order.entity.Order;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer phoneNumber;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;


    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @Builder
    public User(String lastName, String firstName, String email, String password, Integer phoneNumber, Address address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void update(String lastName, String firstName, String password, Integer phoneNumber, Address address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
