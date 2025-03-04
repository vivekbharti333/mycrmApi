package com.spring.object.request;

import java.util.Date;
import lombok.Data;

@Data
public class DonationRequestObject {
	
	private String token;
	private Long id;
	private String donorName;
	private String isdCode;
	private String mobileNumber;
	private String emailId;
	private String panNumber;
	private String address;
	private Double amount = 0D;
	private String currency;
    private String currencyCode;
	private String transactionId;
	private String paymentMode;
	private String paymentType;	
	private String notes;
	private String receiptNumber;
	private String invoiceDownloadStatus;
	private String paymentStatus;
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
	private Long programId;
	private Long currencyMasterId;
	private String currencyName;
	
	private Long invoiceHeaderDetailsId;
	private String invoiceHeaderName;
	private String invoiceNumber;
	
	private Long activeUserCount;
	private Long inactiveUserCount;
	
	private Long todaysCount = 0L;
	private Double todaysAmount = 0D;
	private Long yesterdayCount = 0L;
	private Double yesterdayAmount = 0D;
	private Long monthCount = 0L;
	private Double monthAmount = 0D;
	
	private String searchParam;
	
	private String pgProvider;
	private String merchantId;
	private String saltIndex;
	private String saltKey;
	private String paymentGatewayPageRedirectUrl;
	
	private int respCode;
	private String respMesg;
	
}
