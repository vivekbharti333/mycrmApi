package com.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "payment_gateway_details")
public class PaymentGatewayDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "pg_provider")
	private String pgProvider;
	
	@Column(name = "merchant_id")
	private String merchantId;
	
	@Column(name = "salt_index")
	private String saltIndex;
	
	@Column(name = "salt_key")
	private String saltKey;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	@Column(name = "created_at")
	private Date createdAt;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPgProvider() {
		return pgProvider;
	}

	public void setPgProvider(String pgProvider) {
		this.pgProvider = pgProvider;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSaltIndex() {
		return saltIndex;
	}

	public void setSaltIndex(String saltIndex) {
		this.saltIndex = saltIndex;
	}

	public String getSaltKey() {
		return saltKey;
	}

	public void setSaltKey(String saltKey) {
		this.saltKey = saltKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSuperadminId() {
		return superadminId;
	}

	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "PaymentGatewayDetails [id=" + id + ", pgProvider=" + pgProvider + ", merchantId=" + merchantId
				+ ", saltIndex=" + saltIndex + ", saltKey=" + saltKey + ", status=" + status + ", superadminId="
				+ superadminId + ", createdAt=" + createdAt + "]";
	}
		
		
}
