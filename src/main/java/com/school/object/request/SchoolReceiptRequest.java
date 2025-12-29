package com.school.object.request;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SchoolReceiptRequest {

    // Auth / Meta
    private String token;
    private Long id;

    // Receipt Header
    private String receiptNumber;
    private String admissionNo;
    private String rollNumber;
    private String studentName;
    private String grade;
    private String gradeSection;
    private String academicSession;
    private String installmentName;
    private String paymentMode;   // CASH, ONLINE, CHEQUE
    private Date paymentDate;

    private Double totalAmount;
    private Double discountAmount;
    private Double fineAmount;
    private Double netAmount;
    private String status;        // PAID, PARTIAL, CANCELLED

    // Audit
    private Date createdAt;
    private String createdBy;
    private String createdByName;
    private String superadminId;

    // Receipt Line Items
    private List<SchoolReceiptDetailsRequest> receiptDetails;

    // Response
    private int respCode;
    private String respMesg;
}