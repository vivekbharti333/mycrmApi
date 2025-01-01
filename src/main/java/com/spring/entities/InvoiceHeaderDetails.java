package com.spring.entities;

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
@Table(name = "invoice_header_details")
public class InvoiceHeaderDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "sms_type")
	private String smsType;
	
	@Column(name = "login_id")
	private String loginId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "invoice_initial")
	private String invoiceInitial;
	
	@Column(name = "serial_number")
	private Long serialNumber;
	
	@Lob
	@Column(name = "company_logo")
	private String companyLogo;
	
	@Lob
	@Column(name = "company_stamp")
	private String companyStamp;
	
	@Column(name = "company_first_name")
	private String companyFirstName;
	
	@Column(name = "company_first_name_color")
	private String companyFirstNameColor;

	@Column(name = "company_last_name")
	private String companyLastName;
	
	@Column(name = "company_last_name_color")
	private String companyLastNameColor;
	
	@Column(name = "background_color")
	private String backgroundColor;
	
	@Column(name = "office_address")
	private String officeAddress;
	
	@Column(name = "reg_address")
	private String regAddress;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "alternate_mobile")
	private String alternateMobile;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "gst_number")
	private String gstNumber;
	
	@Column(name = "pan_number")
	private String panNumber;
	
	@Column(name = "account_holder_name")
	private String accountHolderName;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "ifsc_code")
	private String ifscCode;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "branch_name")
	private String branchName;
	
	@Lob
	@Column(name = "thank_you_notes")
	private String thankYouNote;
	
	@Lob
	@Column(name = "footer")
	private String footer;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_At")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	
}
