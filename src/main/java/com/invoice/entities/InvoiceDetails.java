package com.invoice.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "invoice_details")
public class InvoiceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "product_name", length = 200, nullable = false)
	private String productName;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "rate", precision = 10, scale = 2, nullable = false)
	private BigDecimal rate;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "cgst_rate")
	private int cgstRate;

	@Column(name = "cgst_amount")
	private BigDecimal cgstAmount;

	@Column(name = "sgst_rate")
	private int sgstRate;

	@Column(name = "sgst_amount")
	private BigDecimal sgstAmount;

	@Column(name = "igst_rate")
	private int igstRate;

	@Column(name = "igst_amount")
	private BigDecimal igstAmount;

	@Column(name = "amount", precision = 10, scale = 2, nullable = false)
	private BigDecimal amount;

	@Column(name = "status", length = 20)
	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at", updatable = false)
	private Date createdAt;

	@Column(name = "superadmin_id")
	private String superadminId;

}
