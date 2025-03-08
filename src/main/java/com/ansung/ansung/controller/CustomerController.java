package com.ansung.ansung.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.data.CommonDto;
import com.ansung.ansung.data.CustomerDto;
import com.ansung.ansung.repository.CustomerRepository;
import com.ansung.ansung.service.customer.CustomerService;
import com.ansung.ansung.service.geocoder.GeocoderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
	private final CustomerService customerSerivce;
	
	@PostMapping
	public ResponseEntity<Void> addCustomer(@RequestBody CustomerDto customerDto){
		
		if(customerDto.getTel() == null) throw new IllegalArgumentException("전화번호는 필수 입력 값입니다");
		
		customerSerivce.addCustomer(customerDto);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerDto>> getCustomer(){
		List<CustomerDto> customerDtos = customerSerivce.getAllCustomers();
		return new ResponseEntity<List<CustomerDto>>(customerDtos, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteCustomer(@RequestParam(value = "customerId" ,required = true)Long customerId){
		
		customerSerivce.deleteCustomer(customerId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/update")
	public ResponseEntity<CommonDto>  updateCustomer(@RequestParam(value ="customerId" ,required = true)Long customerId){
		
		return new ResponseEntity<CommonDto>(new CommonDto(), HttpStatus.OK);
	}
}
