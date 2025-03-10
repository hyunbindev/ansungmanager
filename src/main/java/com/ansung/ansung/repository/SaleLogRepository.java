package com.ansung.ansung.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ansung.ansung.domain.salelog.SaleLog;

public interface SaleLogRepository extends MongoRepository<SaleLog,String>{
	List<SaleLog> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	List<SaleLog> findBySaleDate(LocalDate date);
}
