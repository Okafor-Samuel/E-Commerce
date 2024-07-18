package com.samuel.ecommerce.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerCreationRequest customerCreationRequest) {
        if(customerCreationRequest == null){
            return null;
        }
        return Customer.builder()
                .id(customerCreationRequest.id())
                .firstName(customerCreationRequest.firstName())
                .lastName(customerCreationRequest.lastName())
                .email(customerCreationRequest.email())
                .address(customerCreationRequest.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return  new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
