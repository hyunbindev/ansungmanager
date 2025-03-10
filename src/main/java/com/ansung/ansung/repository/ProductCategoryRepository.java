package com.ansung.ansung.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ansung.ansung.domain.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory , Long> {

}
