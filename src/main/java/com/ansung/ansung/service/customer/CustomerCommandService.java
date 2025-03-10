package com.ansung.ansung.service.customer;

import org.springframework.stereotype.Service;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.data.CustomerDto;
import com.ansung.ansung.domain.Customer;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerCommandService {
	private final CustomerRepository customerRepository;
	
	//고객 추가
	public void addCustomer(CustomerDto newCustomerDto) {
		Customer customer = newCustomerDto.toEntity();
		customerRepository.save(customer);
	}
	
	//고객 삭제
	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_CUSTOMER));
		customerRepository.delete(customer);
	}
}
