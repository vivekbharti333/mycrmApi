package com.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "payment_details")
public class PaymentDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "pg_provider")
	private String pgProvider;
	
	@Column(name = "merchant_id")
	private String merchantId;
	
	@Column(name = "donor_name")
	private String donorName;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_by")
	private String createdBy;
	
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

	public String getDonorName() {
		return donorName;
	}

	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	
		
		
}
