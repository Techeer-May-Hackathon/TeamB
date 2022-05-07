package com.techeer.svproject.repository;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.address.AddressRepository;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.product.entity.Product;
import com.techeer.svproject.domain.product.repository.ProductRepository;
import com.techeer.svproject.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("주소 저장소 테스트")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    private Address givenAddress;

    private Address address1;

    @BeforeEach
    void setUp(){
        givenAddress = Address.builder()
                .state("CF")
                .city("city")
                .street("street")
                .zipcode(123456)
                .build();

        address1 = addressRepository.save(givenAddress);
    }

    @Test
    @DisplayName("UUID로 repository에 주소 조회")
    void findAddressByIdTest() {
        // given


        // when
        Address address = addressRepository.findById(address1.getAddressId()).orElseThrow(EntityExistsException::new);

        // then
        assertAll(
                () -> assertEquals(address, givenAddress," ")
        );
    }


}
