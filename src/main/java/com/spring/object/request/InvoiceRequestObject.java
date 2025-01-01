package com.spring.object.request;

import java.util.Date;
import java.util.List;
import com.spring.entities.InvoiceDetails;

import lombok.Data;

@Data
public class InvoiceRequestObject {	
	
	private Long id;
	private String invoiceNo;
	private String itemName;
	private float rate;
	private int quantity;
	private float amount;
	private String customerName;
	private String customerId;
	private int totalItem;
	private float totalAmount;
	private String status;
	private String paidBy;
	private Date paidDate;
	private Date dueDate;
	private String transactionId;
	private Date createdAt;
	private String createdBy;
	private String superadminId;
	private String token;
	private String requestFor;
	
	private Long invoiceHeaderDetailsId;
	private String invoiceHeaderName;
	private String invoiceInitial;
	private Long serialNumber;
	private String companyLogo;
	private String companyStamp;
	private String companyFirstName;
	private String companyFirstNameColor;
	private String companyLastName;
	private String companyLastNameColor;
	private String backgroundColor;
	private String officeAddress;
	private String regAddress;
	private String mobileNo;
	private String alternateMobile;
	private String emailId;
	private String website;
	private String thankYouNote;
	private String footer;
	private List<InvoiceDetails> itemDetails;
	private String gstNumber;
	private String panNumber;
	
	private String accountHolderName;
	private String accountNumber;
	private String ifscCode;
	private String bankName;
	private String branchName;
	
	//customer details;
	private CustomerRequestObject customerRequestObject;
	private AddressRequestObject addressRequestObject;
	
	private String receiptDownloadUrl;
	
	private int respCode;
	private String respMesg;
	
	
}
