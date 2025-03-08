package com.ansung.ansung.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ansung.ansung.domain.salelog.SaleLog;

public interface SaleLogRepository extends MongoRepository<SaleLog,String>{
	List<SaleLog> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
