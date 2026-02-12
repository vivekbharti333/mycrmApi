package com.invoice.object.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class InvoiceItemRequest {

	private String invoiceNumber;
	private String productName;
	private String description;
	private BigDecimal rate;
	private int cgstRate;
	private BigDecimal cgstAmount;
	private int sgstRate;
	private BigDecimal sgstAmount;
	private int igstRate;
	private BigDecimal igstAmount;
	private int quantity;
	private BigDecimal amount;
	private String status;
}
