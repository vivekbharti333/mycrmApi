package com.invoice.object.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import lombok.Data;

@Data
public class InvoiceRequestObject {

	private Long companyId;
	
	//Company Details
	private String companyLogo;
	private String companyName;
	private String officeAddress;
	private String regAddress;
	private String mobileNo;
	private String emailId;
	private String website;
	private String gstNumber;
	private String panNumber;

	//Customer Details
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerGstNumber;
    private String billingAddress;
    private String deliveryAddresses;
	
	private String invoiceNumber;
	private Date invoiceDate;
	private Date dueDate;
	
	private BigDecimal subtotal;
	private BigDecimal discount;
	private BigDecimal taxAmount;
	private BigDecimal totalAmount;

	private String status;
	private String paymentMode;
	private String transactionId;
	private String paymentStatus;
	private String invoiceStatus;

    private Date createdAt;
	private String superadminId;
	private String createdBy;

	private List<InvoiceItemRequest> items;
	
	private String requestFor;
	private int respCode;
	private String respMesg;
}
