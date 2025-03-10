package com.ansung.ansung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ansung.ansung.domain.Product;
import com.ansung.ansung.domain.ProductCategory;

public interface ProductRepository extends JpaRepository<Product , Long>{
	List<Product> findByDeletedFalse();

	List<Product> findByCategory(ProductCategory category);
}
