package com.ecommerce.customerservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String userName;

	@NotBlank
	private String userPassword;

	@NotBlank
	private String name;

	@Email(message = "Enter valid email")
	private String email;

	private String country;

	public Customer updateWith(Customer customer) {
		return new Customer(this.id, customer.userName, customer.userPassword, customer.name, customer.email,
				customer.country);
	}
}
