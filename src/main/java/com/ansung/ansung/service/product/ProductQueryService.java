package com.ansung.ansung.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.data.ProductCategoryDto;
import com.ansung.ansung.data.ProductDto;
import com.ansung.ansung.domain.Product;
import com.ansung.ansung.domain.ProductCategory;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.ProductCategoryRepository;
import com.ansung.ansung.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly= true)
public class ProductQueryService {
	private final ProductRepository productRepository;
	private final ProductCategoryRepository categoryRepository;
	public List<ProductDto> getAllProducts(){
		List<Product> productEntitys = productRepository.findByDeletedFalse();
		
		List<ProductDto> productDtos = new ArrayList<>();
		
		for(Product entity : productEntitys) {
			ProductDto productDto = ProductDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.category(ProductCategoryDto
							.builder()
							.id(entity.getCategory().getId())
							.name(entity.getCategory().getName())
							.build())
					.size(entity.getSize())
					.price(entity.getPrice())
					.build();
			productDtos.add(productDto);
		}
		
		return productDtos;
	}
	public List<ProductDto> getProductByCategory(Long categoryId){
		ProductCategory category = categoryRepository.findById(categoryId)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_CATEGORY));
		List<Product> products = productRepository.findByCategory(category);
		List<ProductDto> dtos = new ArrayList<>();
		for(Product entity : products) {
			ProductDto dto = ProductDto.builder()
					.id(entity.getId())
					.name(entity.getName())
					.category(ProductCategoryDto
							.builder()
							.id(entity.getCategory().getId())
							.name(entity.getCategory().getName())
							.build())
					.size(entity.getSize())
					.price(entity.getPrice())
					.build();
			dtos.add(dto); 
		}
		return dtos;
	}
}
