package com.ansung.ansung.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansung.ansung.data.ProductCategoryDto;
import com.ansung.ansung.data.ProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductCommandService productCommandService;
	private final ProductQueryService productQueryService;
	
	public void addProduct(ProductDto newProductDto) {
		productCommandService.addProduct(newProductDto);
	}
	
	public void deleteProduct(Long productId) {
		
		productCommandService.deleteProduct(productId);
	}
	
	public List<ProductDto> getAllProducts(){
		return productQueryService.getAllProducts();
	}
	
	public List<ProductDto> getProductByCategory(Long categoryId){
		return productQueryService.getProductByCategory(categoryId);
	}
}
