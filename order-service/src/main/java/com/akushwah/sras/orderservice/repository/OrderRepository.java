package com.akushwah.sras.orderservice.repository;

import com.akushwah.sras.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
