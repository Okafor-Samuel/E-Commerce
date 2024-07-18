package com.samuel.ecommerce.customer;

import com.samuel.ecommerce.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    public String createCustomer(CustomerCreationRequest customerCreationRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerCreationRequest));
        return customer.getId();

    }

    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        var customer = customerRepository.findById(customerUpdateRequest.id())
                .orElseThrow(()-> new CustomerNotFoundException
                        (String.format("Cannot update customer:: No customer with id:: %s", customerUpdateRequest.id())));
        mergerCustomer(customer, customerUpdateRequest);
        customerRepository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerUpdateRequest customerUpdateRequest) {
        if(StringUtils.isNotBlank(customerUpdateRequest.firstName())){
            customer.setFirstName(customerUpdateRequest.firstName());
        }
        if(StringUtils.isNotBlank(customerUpdateRequest.lastName())){
            customer.setLastName(customerUpdateRequest.lastName());
        }
        if(StringUtils.isNotBlank(customerUpdateRequest.email())){
            customer.setEmail(customerUpdateRequest.email());
        }
        if(Objects.nonNull(customerUpdateRequest.address())){
            customer.setAddress(customerUpdateRequest.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()-> new CustomerNotFoundException(String.format("No customer with id:: %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
