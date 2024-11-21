package com.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "payment_mode_by_superadmin")
public class PaymentModeBySuperadmin {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "payment_mode_ids")
	private String paymentModeIds;
	
	@Column(name = "superadmin_id")
	private String superadminId;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentModeIds() {
		return paymentModeIds;
	}

	public void setPaymentModeIds(String paymentModeIds) {
		this.paymentModeIds = paymentModeIds;
	}

	public String getSuperadminId() {
		return superadminId;
	}

	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}

		
		
}
