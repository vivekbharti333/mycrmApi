package com.invoice.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "invoice_number")
public class InvoiceNumber {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;
    
    // Company Details
	@Column(name = "company_logo")
	private String companyLogo;
	
	@Column(name = "company_name")
	private String companyName;

	@Column(name = "office_address")
	private String officeAddress;
	
	@Column(name = "reg_address")
	private String regAddress;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "gst_number")
	private String gstNumber;
	
	@Column(name = "pan_number")
	private String panNumber;
    
    
    // Customer Details
    @Column(name = "customer_name", length = 200, nullable = false)
    private String customerName;

    @Column(name = "customer_email", length = 150)
    private String customerEmail;

    @Column(name = "customer_phone", length = 20)
    private String customerPhone;

    @Column(name = "customer_gst_number", length = 20)
    private String customerGstNumber;

    @Column(name = "billing_address", columnDefinition = "TEXT")
    private String billingAddress;
    
    @Column(name = "delivery_address", columnDefinition = "TEXT")
    private String deliveryAddresses;
    
    // Invoice Number Details
    @Column(name = "invoice_number", length = 50, nullable = false, unique = true)
    private String invoiceNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "discount", precision = 12, scale = 2)
    private BigDecimal discount;

    @Column(name = "total_tax_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalTaxAmount;
    
    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "status", length = 20)
    private String status;
    
    @Column(name = "payment_mode", length = 20)
    private String paymentMode;
    
	@Column(name = "transaction_id")
	private String transactionId;
    
    @Column(name = "payment_status", length = 20)
    private String paymentStatus;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "invoice_status", length = 20)
    private String invoiceStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "superadmin_id")
    private String superadminId;
}
