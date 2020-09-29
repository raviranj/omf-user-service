package com.mindtree.omf.user.management.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
public class UserOrderRequest {
	private Long userId;
	private String paymentType;
	private String instruction;
	public Long restaurantId;
	public List<OrderDishDetailRequest> orderDishDetailList;
}
