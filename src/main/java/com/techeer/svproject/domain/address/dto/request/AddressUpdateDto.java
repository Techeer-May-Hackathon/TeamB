package com.techeer.svproject.domain.address.dto.request;
import com.techeer.svproject.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateDto {
    private UUID addressId;
    private String state;
    private String city;
    private String street;
    private int zipcode;

    public static AddressUpdateDto fromEntity(Address ads){
        return AddressUpdateDto.builder()
                .addressId(ads.getAddressId())
                .state(ads.getState())
                .city(ads.getCity())
                .street(ads.getStreet())
                .zipcode(ads.getZipcode())
                .build();
    }
}
