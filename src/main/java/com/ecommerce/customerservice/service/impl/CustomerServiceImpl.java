package com.ecommerce.customerservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.customerservice.entity.Customer;
import com.ecommerce.customerservice.repository.CustomerRepository;
import com.ecommerce.customerservice.service.CustomerService;
import com.ecommerce.sharedlibrary.exception.EcommerceException;
import com.ecommerce.sharedlibrary.model.CustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<CustomerDto> getAllCustomers() {
		List<CustomerDto> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customer -> {
			customers.add(mapper.map(customer, CustomerDto.class));
		});
		return customers;
	}

	@Override
	public CustomerDto getCustomerById(long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return (customer.isPresent() ? mapper.map(customer.get(), CustomerDto.class) : null);
	}

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer customer = mapper.map(customerDto, Customer.class);
		return mapper.map(customerRepository.save(customer), CustomerDto.class);
	}

	@Override
	public CustomerDto updateCustomer(long id, CustomerDto customerDto) {
		Optional<Customer> updatedCustomer = customerRepository.findById(id).map(existingCustomer -> {
			Customer customer = mapper.map(customerDto, Customer.class);
			return customerRepository.save(existingCustomer.updateWith(customer));
		});

		return (updatedCustomer.isPresent() ? mapper.map(updatedCustomer.get(), CustomerDto.class) : null);
	}

	@Override
	public void deleteCustomer(long id) {
		// Do not delete if any of the customer order status is - INTRANSIT, PAYMENTDUE,
		// PROCESSING

		if (getCustomerById(id) != null) {
			customerRepository.deleteById(id);
			LOGGER.info("Customer deleted Successfully");
		} else {
			throw new EcommerceException("customer-not-found", String.format("Customer with id=%d not found", id),
					HttpStatus.NOT_FOUND);
		}
	}

}
