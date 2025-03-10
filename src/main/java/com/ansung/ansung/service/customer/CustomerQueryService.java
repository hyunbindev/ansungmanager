package com.ansung.ansung.service.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansung.ansung.data.CustomerDto;
import com.ansung.ansung.domain.Customer;
import com.ansung.ansung.repository.CustomerRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerQueryService {
	private final CustomerRepository customerRepository;
	
	//모든 고객 리스트 조회
	public List<CustomerDto> getAllCustomerList(){
		List<Customer> customerEntitys = customerRepository.findAll();
		List<CustomerDto> customerDtos = new ArrayList<>();
		
		for(Customer entity : customerEntitys) {
			CustomerDto customerDto = CustomerDto.builder()
					.id(entity.getId())
					.tel(entity.getTel())
					.jibunAddress(entity.getJibunAddress())
					.roadAddress(entity.getRoadAddress())
					.remarks(entity.getRemarks())
					.Lat(entity.getLat())
					.Lng(entity.getLng())
					.build();
			customerDtos.add(customerDto);
		}
		
		return customerDtos;
	}
}
