package com.techeer.svproject.domain.address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID>{
    // 주소 생성/조회/수정/삭제
}
