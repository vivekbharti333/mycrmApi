package com.spring.object.request;

import java.util.Date;
import java.util.List;
import com.spring.entities.InvoiceDetails;

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
	
	private int respCode;
	private String respMesg;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSuperadminId() {
		return superadminId;
	}
	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRequestFor() {
		return requestFor;
	}
	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}
	public Long getInvoiceHeaderDetailsId() {
		return invoiceHeaderDetailsId;
	}
	public void setInvoiceHeaderDetailsId(Long invoiceHeaderDetailsId) {
		this.invoiceHeaderDetailsId = invoiceHeaderDetailsId;
	}
	public String getInvoiceHeaderName() {
		return invoiceHeaderName;
	}
	public void setInvoiceHeaderName(String invoiceHeaderName) {
		this.invoiceHeaderName = invoiceHeaderName;
	}
	public String getInvoiceInitial() {
		return invoiceInitial;
	}
	public void setInvoiceInitial(String invoiceInitial) {
		this.invoiceInitial = invoiceInitial;
	}
	public Long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getCompanyStamp() {
		return companyStamp;
	}
	public void setCompanyStamp(String companyStamp) {
		this.companyStamp = companyStamp;
	}
	public String getCompanyFirstName() {
		return companyFirstName;
	}
	public void setCompanyFirstName(String companyFirstName) {
		this.companyFirstName = companyFirstName;
	}
	public String getCompanyFirstNameColor() {
		return companyFirstNameColor;
	}
	public void setCompanyFirstNameColor(String companyFirstNameColor) {
		this.companyFirstNameColor = companyFirstNameColor;
	}
	public String getCompanyLastName() {
		return companyLastName;
	}
	public void setCompanyLastName(String companyLastName) {
		this.companyLastName = companyLastName;
	}
	public String getCompanyLastNameColor() {
		return companyLastNameColor;
	}
	public void setCompanyLastNameColor(String companyLastNameColor) {
		this.companyLastNameColor = companyLastNameColor;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getRegAddress() {
		return regAddress;
	}
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAlternateMobile() {
		return alternateMobile;
	}
	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getThankYouNote() {
		return thankYouNote;
	}
	public void setThankYouNote(String thankYouNote) {
		this.thankYouNote = thankYouNote;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public List<InvoiceDetails> getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(List<InvoiceDetails> itemDetails) {
		this.itemDetails = itemDetails;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public CustomerRequestObject getCustomerRequestObject() {
		return customerRequestObject;
	}
	public void setCustomerRequestObject(CustomerRequestObject customerRequestObject) {
		this.customerRequestObject = customerRequestObject;
	}
	public AddressRequestObject getAddressRequestObject() {
		return addressRequestObject;
	}
	public void setAddressRequestObject(AddressRequestObject addressRequestObject) {
		this.addressRequestObject = addressRequestObject;
	}
	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public String getRespMesg() {
		return respMesg;
	}
	public void setRespMesg(String respMesg) {
		this.respMesg = respMesg;
	}
	
}
