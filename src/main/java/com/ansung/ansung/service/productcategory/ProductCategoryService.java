package com.ansung.ansung.service.productcategory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.data.ProductCategoryDto;
import com.ansung.ansung.domain.ProductCategory;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
	private final ProductCategoryRepository productCategoryRepository;
	
	public void addCategory(ProductCategoryDto dto) {
		ProductCategory entity = dto.toEntity();
		productCategoryRepository.save(entity);
	}
	
	public List<ProductCategoryDto> getAllCategory(){
		List<ProductCategory> entitys = productCategoryRepository.findAll();
		List<ProductCategoryDto> dtos = new ArrayList<>();
		for(ProductCategory entity :entitys) {
			ProductCategoryDto dto = ProductCategoryDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.build();
			dtos.add(dto);
		}
		return dtos;
	}
	
	public void deleteCategory(Long id) {
		ProductCategory entity = productCategoryRepository.findById(id)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_PRODUCT));
		productCategoryRepository.delete(entity);
	}
}
