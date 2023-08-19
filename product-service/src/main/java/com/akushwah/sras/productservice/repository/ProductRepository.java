package com.akushwah.sras.productservice.repository;

import com.akushwah.sras.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
