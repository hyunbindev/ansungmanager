package com.ansung.ansung.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.data.ProductDto;
import com.ansung.ansung.domain.Product;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandService {
	private final ProductRepository productRepository;
	
	public void addProduct(ProductDto newProductDto) {
		Product newProduct = newProductDto.toEntity();
		productRepository.save(newProduct);
	}
	
	public void deleteProduct(Long productId) {
		Product deleteProduct = productRepository.findById(productId)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_PRODUCT));
		
		deleteProduct.setDeleted(true);
		
		productRepository.save(deleteProduct);
	}
}
