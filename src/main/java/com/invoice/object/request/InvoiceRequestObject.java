package com.invoice.object.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class InvoiceRequestObject {

	private Long companyId;
	private Long customerId;
	private String invoiceNumber;
	private Date invoiceDate;
	private Date dueDate;

	private BigDecimal subtotal;
	private BigDecimal discount;
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
