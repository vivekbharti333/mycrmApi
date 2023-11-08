package com.spring.object.request;

import java.util.Date;


public class DonationRequestObject {
	
	private String token;
	private Long id;
	private String donorName;
	private String mobileNumber;
	private String emailId;
	private String panNumber;
	private String address;
	private Double amount = 0D;
	private String transactionId;
	private String paymentMode;
	private String paymentType;	
	private String notes;
	private String receiptNumber;
	private String invoiceDownloadStatus;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String createdbyName;
	private String loginId;
	private String teamLeaderId;
	private String superadminId;
	private String smsResponse;
	
	private String roleType;
	private String requestedFor;
	private Date firstDate;
	private Date lastDate;
	
	private String programName;
	private Double programAmount;
	
	private Long activeUserCount;
	private Long inactiveUserCount;
	
	private Long todaysCount = 0L;
	private Double todaysAmount = 0D;
	private Long yesterdayCount = 0L;
	private Double yesterdayAmount = 0D;
	private Long monthCount = 0L;
	private Double monthAmount = 0D;
	
	
	private int respCode;
	private String respMesg;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDonorName() {
		return donorName;
	}
	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getReceiptNumber() {
		return receiptNumber;
	}
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}
	public String getInvoiceDownloadStatus() {
		return invoiceDownloadStatus;
	}
	public void setInvoiceDownloadStatus(String invoiceDownloadStatus) {
		this.invoiceDownloadStatus = invoiceDownloadStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedbyName() {
		return createdbyName;
	}
	public void setCreatedbyName(String createdbyName) {
		this.createdbyName = createdbyName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getTeamLeaderId() {
		return teamLeaderId;
	}
	public void setTeamLeaderId(String teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}
	public String getSuperadminId() {
		return superadminId;
	}
	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}
	public String getSmsResponse() {
		return smsResponse;
	}
	public void setSmsResponse(String smsResponse) {
		this.smsResponse = smsResponse;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}
	public Date getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public Double getProgramAmount() {
		return programAmount;
	}
	public void setProgramAmount(Double programAmount) {
		this.programAmount = programAmount;
	}
	public Long getActiveUserCount() {
		return activeUserCount;
	}
	public void setActiveUserCount(Long activeUserCount) {
		this.activeUserCount = activeUserCount;
	}
	public Long getInactiveUserCount() {
		return inactiveUserCount;
	}
	public void setInactiveUserCount(Long inactiveUserCount) {
		this.inactiveUserCount = inactiveUserCount;
	}
	public Long getTodaysCount() {
		return todaysCount;
	}
	public void setTodaysCount(Long todaysCount) {
		this.todaysCount = todaysCount;
	}
	public Double getTodaysAmount() {
		return todaysAmount;
	}
	public void setTodaysAmount(Double todaysAmount) {
		this.todaysAmount = todaysAmount;
	}
	public Long getYesterdayCount() {
		return yesterdayCount;
	}
	public void setYesterdayCount(Long yesterdayCount) {
		this.yesterdayCount = yesterdayCount;
	}
	public Double getYesterdayAmount() {
		return yesterdayAmount;
	}
	public void setYesterdayAmount(Double yesterdayAmount) {
		this.yesterdayAmount = yesterdayAmount;
	}
	public Long getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Long monthCount) {
		this.monthCount = monthCount;
	}
	public Double getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(Double monthAmount) {
		this.monthAmount = monthAmount;
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
	
	
	@Override
	public String toString() {
		return "DonationRequestObject [token=" + token + ", id=" + id + ", donorName=" + donorName + ", mobileNumber="
				+ mobileNumber + ", emailId=" + emailId + ", panNumber=" + panNumber + ", address=" + address
				+ ", amount=" + amount + ", transactionId=" + transactionId + ", paymentMode=" + paymentMode
				+ ", paymentType=" + paymentType + ", notes=" + notes + ", receiptNumber=" + receiptNumber
				+ ", invoiceDownloadStatus=" + invoiceDownloadStatus + ", status=" + status + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", createdbyName=" + createdbyName
				+ ", loginId=" + loginId + ", teamLeaderId=" + teamLeaderId + ", superadminId=" + superadminId
				+ ", roleType=" + roleType + ", requestedFor=" + requestedFor + ", firstDate=" + firstDate
				+ ", lastDate=" + lastDate + ", programName=" + programName + ", programAmount=" + programAmount
				+ ", activeUserCount=" + activeUserCount + ", inactiveUserCount=" + inactiveUserCount + ", todaysCount="
				+ todaysCount + ", todaysAmount=" + todaysAmount + ", yesterdayCount=" + yesterdayCount
				+ ", yesterdayAmount=" + yesterdayAmount + ", monthCount=" + monthCount + ", monthAmount=" + monthAmount
				+ ", respCode=" + respCode + ", respMesg=" + respMesg + "]";
	}
	
	
	
}
