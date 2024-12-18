package com.spring.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "donation_details")
public class DonationDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "donor_name")
	private String donorName;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "pan_number")
	private String panNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "program_name")
	private String programName;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "currency_code")
    private String currencyCode;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "payment_mode")
	private String paymentMode;
	
	@Column(name = "payment_type")
	private String paymentType;
	
	@Column(name = "receipt_number")
	private String receiptNumber;
	
	@Column(name = "invoice_header_details_id")
	private Long invoiceHeaderDetailsId;
	
	@Column(name = "invoice_header_name")
	private String invoiceHeaderName;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "invoice_download_status")
	private String invoiceDownloadStatus;
	
	@Column(name = "payment_status")
	private String paymentStatus;
	
	@Column(name = "status")
	private String status;
	
	@Lob
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_At")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "createdby_name")
	private String createdbyName;
	
	@Column(name = "loginid")
	private String loginId;
	
	@Column(name = "team_leader_id")
	private String teamLeaderId;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
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

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Long getInvoiceHeaderDetailsId() {
		return invoiceHeaderDetailsId;
	}

	public void setInvoiceHeaderDetailsId(Long invoiceHeaderDetailsId) {
		this.invoiceHeaderDetailsId = invoiceHeaderDetailsId;
	}

	public String getInvoiceHeaderName() {
		return invoiceHeaderName;
	}

	public void setInvoiceHeaderName(String invoiceHeaderName) {
		this.invoiceHeaderName = invoiceHeaderName;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDownloadStatus() {
		return invoiceDownloadStatus;
	}

	public void setInvoiceDownloadStatus(String invoiceDownloadStatus) {
		this.invoiceDownloadStatus = invoiceDownloadStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedbyName() {
		return createdbyName;
	}

	public void setCreatedbyName(String createdbyName) {
		this.createdbyName = createdbyName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(String teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public String getSuperadminId() {
		return superadminId;
	}

	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}

	
}
