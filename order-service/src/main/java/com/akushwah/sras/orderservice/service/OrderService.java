package com.akushwah.sras.orderservice.service;

import com.akushwah.sras.orderservice.dto.InventoryResponse;
import com.akushwah.sras.orderservice.dto.OrderLineItemsDto;
import com.akushwah.sras.orderservice.dto.OrderRequest;
import com.akushwah.sras.orderservice.model.Order;
import com.akushwah.sras.orderservice.model.OrderLineItems;
import com.akushwah.sras.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToOrderLineItems).toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponses = webClient.get().uri("http://localhost:8092/api/v1/inventory", uriBuilder -> uriBuilder.queryParam("sku-code", skuCodes).build())
                        .retrieve().bodyToMono(InventoryResponse[].class).block();

        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(!allProductsInStock){
            log.info("Order Failed : {}", orderRequest);
            throw new IllegalArgumentException("Out of Stock");
        } else {
            orderRepository.save(order);
        }
        log.info("Order Placed Successfully");
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .skuCode(orderLineItemsDto.getSkuCode())
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }
}
