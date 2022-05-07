package com.techeer.svproject.domain.address.dto.request;
import com.techeer.svproject.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressCreateDto {
    private String state;
    private String city;
    private Integer zipcode;
    private String street;

    public Address toEntity() {
        return Address.builder()
                .state(state)
                .city(city)
                .zipcode(zipcode)
                .street(street)
                .build();
    }
}
