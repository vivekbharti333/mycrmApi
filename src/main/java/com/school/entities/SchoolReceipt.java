package com.school.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "school_receipt")
public class SchoolReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "admission_no")
    private String admissionNo;

    @Column(name = "roll_number")
    private String rollNumber;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "grade")
    private String grade;

    @Column(name = "grade_section")
    private String gradeSection;

    @Column(name = "academic_session")
    private String academicSession;

    @Column(name = "installment_name")
    private String installmentName;

    @Column(name = "payment_mode")
    private String paymentMode; // CASH, ONLINE, CHEQUE

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "fine_amount")
    private Double fineAmount;

    @Column(name = "net_amount")
    private Double netAmount;

    @Column(name = "status")
    private String status; // PAID, PARTIAL, CANCELLED

	/// Audit Fields
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_by_name")
	private String createdByName;

	@Column(name = "superadmin_id")
	private String superadminId;

}



