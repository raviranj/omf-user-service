package com.mindtree.omf.user.management.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Dishes implements Serializable {
	private static final long serialVersionUID = -6245717317919880483L;
	private long dishId;
	private String dishName;
	private double dishPrice;
	private String dishType;

}
