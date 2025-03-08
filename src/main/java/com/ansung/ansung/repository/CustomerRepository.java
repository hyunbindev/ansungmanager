package com.ansung.ansung.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ansung.ansung.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer , Long>{

}
