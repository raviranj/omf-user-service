package com.mindtree.omf.user.management.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SearchRestaurentResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private long restaurantId;
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantOffer;
	private long minimumOrder;
	private long distance;
	private double budget;
	private double tax;
	private Double averageRating;
	private Map<String, List<Dishes>> menu;
}
