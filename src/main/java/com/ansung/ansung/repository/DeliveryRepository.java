package com.ansung.ansung.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ansung.ansung.domain.order.Delivery;

public interface DeliveryRepository extends MongoRepository<Delivery,String>{
	 List<Delivery> findByPending(boolean pending);
}