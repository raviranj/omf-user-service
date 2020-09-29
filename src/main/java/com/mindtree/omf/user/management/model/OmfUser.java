package com.mindtree.omf.user.management.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "USER_INFO")
public class OmfUser {

	public enum Role {
		USER, ADMIN, ANONYMOUS
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private Long userId;

	@Column(name = "name")
	private String name;

	@Column(name = "contactNumber")
	private Long contactNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
	
	@Column
	@CreationTimestamp
	private LocalDateTime creationTime;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;
}
