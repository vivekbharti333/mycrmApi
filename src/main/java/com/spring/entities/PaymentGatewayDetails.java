package com.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "payment_gateway_details")
public class PaymentGatewayDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "pg_provider")
	private String pgProvider;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "merchant_id")
	private String merchantId;
	
	@Column(name = "salt_index")
	private String saltIndex;
	
	@Column(name = "salt_key")
	private String saltKey;
	
	@Column(name = "redirect_url")
	private String redirectUrl;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	@Column(name = "created_at")
	private Date createdAt;
		
}
