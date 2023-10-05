package com.ecommerce.customerservice.service;

import java.util.List;

import com.ecommerce.sharedlibrary.model.CustomerDto;

public interface CustomerService {

	List<CustomerDto> getAllCustomers();

	CustomerDto getCustomerById(long id);

	CustomerDto createCustomer(CustomerDto customer);

	CustomerDto updateCustomer(long id, CustomerDto customer);

	void deleteCustomer(long id);
}
