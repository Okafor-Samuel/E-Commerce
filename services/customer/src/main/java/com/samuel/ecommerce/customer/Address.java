package com.samuel.ecommerce.customer;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
@Validated
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;

}
