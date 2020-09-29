package com.mindtree.omf.user.management.model;

import lombok.Data;

@Data
public class SearchByRequest {
	private String dishType;
	private long distance;
	private double budget;
	private double rating;
	private long restaurantId;
	
	
}
