package com.ngo.entities;

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
	
	@Column(name = "isd_code")
	private String isdCode;
	
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
	
	@Column(name = "called")
	private String called;
	
	}
