package com.akushwah.sras.productservice.service;

import com.akushwah.sras.productservice.dto.ProductRequest;
import com.akushwah.sras.productservice.dto.ProductResponse;
import com.akushwah.sras.productservice.model.Product;
import com.akushwah.sras.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();

        product = productRepository.save(product);
        log.info("Product is saved successfully - {}",product.getId());

        ProductResponse response = convertToProductResponse(product);
        return response;
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();
    }
}
