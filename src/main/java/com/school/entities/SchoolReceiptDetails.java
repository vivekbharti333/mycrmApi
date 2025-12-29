package com.school.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "school_receipt_details")
public class SchoolReceiptDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "receipt_number")
	private String receiptNumber;

	@Column(name = "student_name")
	private String studentName;
	
	@Column(name = "fee_type")
	private String feeType; // Tuition, Transport, Exam, etc.

	@Column(name = "amount")
	private Double amount;

	// Audit Fields
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_by_name")
	private String createdByName;

	@Column(name = "superadmin_id")
	private String superadminId;

}

