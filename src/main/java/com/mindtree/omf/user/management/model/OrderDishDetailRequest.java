package com.mindtree.omf.user.management.model;

import lombok.Data;

@Data
public class OrderDishDetailRequest {
	public Long dishId;
	public Long quantity;
	public double price;
}
