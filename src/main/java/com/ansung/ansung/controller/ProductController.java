package com.ansung.ansung.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.data.CommonDto;
import com.ansung.ansung.data.ProductDto;
import com.ansung.ansung.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	@GetMapping
	public ResponseEntity<List<ProductDto>> getProduct(){
		List<ProductDto> productDtos = productService.getAllProducts();
		return new ResponseEntity<List<ProductDto>>(productDtos,HttpStatus.OK);
	}
	@GetMapping("/category")
	public ResponseEntity<List<ProductDto>>getProductByCategory(@RequestParam("categoryId")Long categoryId){
		List<ProductDto> response = productService.getProductByCategory(categoryId);
		return new ResponseEntity<List<ProductDto>>(response , HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Void> addProduct(@RequestBody ProductDto newProductDto){
		productService.addProduct(newProductDto);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<CommonDto> deleteProduct(@RequestParam("productId")Long productId){
		productService.deleteProduct(productId);
		return new ResponseEntity<CommonDto>(new CommonDto() , HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/update")
	public ResponseEntity<CommonDto> updateProduct(@RequestBody ProductDto productDto){
		
		return new ResponseEntity<CommonDto>(new CommonDto() , HttpStatus.OK);
	}
}
