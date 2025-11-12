package com.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "api_details")
public class ApiDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "service_provider")
	private String serviceProvider;
	
	@Column(name = "service_for")
	private String serviceFor;
	
	@Column(name = "user_name ")
	private String userName;
	
	@Column(name = "api_keys")
	private String apiKeys;
	
	@Column(name = "api_value")
	private String apiValue;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	
}
