package com.ansung.ansung.service.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ansung.ansung.data.CustomerDto;
import com.ansung.ansung.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerCommandService customerCommandService;
	private final CustomerQueryService customerQueryService;
	
	public void addCustomer(CustomerDto customerDto) {
		customerCommandService.addCustomer(customerDto);
	}
	
	public void deleteCustomer(Long customerId) {
		customerCommandService.deleteCustomer(customerId);
	}
	
	public List<CustomerDto> getAllCustomers() {
		return customerQueryService.getAllCustomerList();
	}
}
