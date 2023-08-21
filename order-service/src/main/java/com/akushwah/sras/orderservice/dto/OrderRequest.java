package com.akushwah.sras.orderservice.dto;

import com.akushwah.sras.orderservice.model.OrderLineItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long id;
    private String orderNumber;
    private List<OrderLineItemsDto> orderLineItemsDtoList;

}
