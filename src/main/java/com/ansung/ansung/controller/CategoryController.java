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

import com.ansung.ansung.data.ProductCategoryDto;
import com.ansung.ansung.service.productcategory.ProductCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
	private final ProductCategoryService productCategoryService;
	@PostMapping
	public ResponseEntity<Void> addCategory(@RequestBody ProductCategoryDto dto){
		productCategoryService.addCategory(dto);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<ProductCategoryDto>> getCategory(){
		List<ProductCategoryDto>response = productCategoryService.getAllCategory();
		return new ResponseEntity<List<ProductCategoryDto>>(response,HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteCategory(@RequestParam("categoryId")Long categoryId){
		productCategoryService.deleteCategory(categoryId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
