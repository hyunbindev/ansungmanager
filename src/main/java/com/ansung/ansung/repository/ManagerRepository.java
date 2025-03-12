package com.ansung.ansung.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ansung.ansung.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager , Long>{
	Optional<Manager> findByName(String name);
}
