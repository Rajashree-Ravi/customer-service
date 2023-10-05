package com.ecommerce.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.customerservice.service.CustomerService;
import com.ecommerce.sharedlibrary.exception.EcommerceException;
import com.ecommerce.sharedlibrary.model.CustomerDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage customers in e-commerce application")
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping
	@ApiOperation(value = "View all customers", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all customers"),
			@ApiResponse(code = 204, message = "Customers list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<CustomerDto>> getAllCustomers() {

		List<CustomerDto> customers = customerService.getAllCustomers();
		if (customers.isEmpty())
			throw new EcommerceException("no-content", "Customers list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific customer with the specified customer id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved customer with the customer id"),
			@ApiResponse(code = 404, message = "Customer with specified customer id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") long id) {

		CustomerDto customer = customerService.getCustomerById(id);
		if (customer != null)
			return new ResponseEntity<>(customer, HttpStatus.OK);
		else
			throw new EcommerceException("customer-not-found", String.format("Customer with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new customer", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a customer"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {
		return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a customer information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated customer information"),
			@ApiResponse(code = 404, message = "Customer with specified customer id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") long id, @RequestBody CustomerDto customer) {

		CustomerDto updatedCustomer = customerService.updateCustomer(id, customer);
		if (updatedCustomer != null)
			return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
		else
			throw new EcommerceException("customer-not-found", String.format("Customer with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a customer", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted customer information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {

		customerService.deleteCustomer(id);
		return new ResponseEntity<>("Customer deleted successfully", HttpStatus.NO_CONTENT);
	}
}
