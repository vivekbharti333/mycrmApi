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
@Table(name = "invoice_number")
public class InvoiceNumber {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "invoice_number", length = 50, nullable = false, unique = true)
    private String invoiceNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "discount", precision = 12, scale = 2)
    private BigDecimal discount;

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
