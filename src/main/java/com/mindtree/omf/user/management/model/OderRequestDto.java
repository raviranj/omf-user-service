package com.mindtree.omf.user.management.model;

import java.util.List;

import lombok.Data;

@Data
public class OderRequestDto {
	private Long userId;
	private String paymentType;
	private String instruction;
	private String delivaryBoy;
	private String username;
	private String offer;
	private List<OrderDishDetailRequest> orderDishDetailList;
	private SearchRestaurentResponse restaurantinfo;
}
