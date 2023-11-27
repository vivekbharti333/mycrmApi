package com.spring.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.common.SmsHelper;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.SmsTemplateDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RequestFor;
import com.spring.enums.RoleType;
import com.spring.enums.SmsType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.helper.SmsTemplateHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;


@Service
public class DonationService {
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private SmsTemplateHelper smsTemplateHelper;
	
	@Autowired
	private SmsHelper smsHelper;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private InvoiceHelper invoiceHelper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private LocalDate localDate = LocalDate.now();
	private LocalDate nextday = localDate.plus(1, ChronoUnit.DAYS);
	private LocalDate preday = localDate.minus(1, ChronoUnit.DAYS);
	private LocalDate firstDateOfMonth = localDate.withDayOfMonth(1);
	private LocalDate lastDateOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
	
	private Date todayDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date tomorrowDate = Date.from(nextday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date previousDate = Date.from(preday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date firstDateMonth = Date.from(firstDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date lastDateMonth = Date.from(lastDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
	
	
	
	@SuppressWarnings("static-access")
	@Transactional
	public DonationRequestObject addDonation(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);
		
//		      logger.info("Add Donation. Is valid? : " + donationRequest.getLoginId() + " is " + isValid);

//		if (isValid) {
//			
//			//Validate created By
//			UserDetails existsUserDetails = userHelper.getUserDetailsByLoginIdAndSuperadminId(donationRequest.getCreatedBy(), donationRequest.getSuperadminId());
//			if(existsUserDetails == null) {
//				donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
//				donationRequest.setRespMesg("Invalid Createdby");
//				return donationRequest; 
//			}
		
		if(donationRequest.getInvoiceHeaderDetailsId() == null) {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Please Select Receipt Type");
			return donationRequest; 
		}
		
		if(donationRequest.getMobileNumber() == null) {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Enter Mobile Number");
			return donationRequest; 
		}
		
		if(donationRequest.getAmount() == 0) {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Amount can not be null or Zero");
			return donationRequest; 
		}
		
		
		
		if(donationRequest.getProgramName() == null || donationRequest.getProgramName().isEmpty() || donationRequest.getProgramName().equals("")) {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Please Program");
			return donationRequest; 
		}
		if((donationRequest.getPaymentMode() == null) || donationRequest.getPaymentMode().equalsIgnoreCase(""))  {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Please select payment mode");
			return donationRequest; 
		}
		
		
			//Generate Receipt Number
			String rendomNumber = userHelper.generateRandomChars("ABCD145pqrs678abcdef90EF9GHxyzIJKL5MNOPQRghijS1234560TUVWXYlmnoZ1234567tuvw890", 4);
			String receiptNumber = donationRequest.getSuperadminId().substring(0, 4)+rendomNumber+donationRequest.getMobileNumber().substring(7, 10);
			donationRequest.setReceiptNumber(receiptNumber);
			
			InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderById(donationRequest.getInvoiceHeaderDetailsId());
			if(invoiceHeader != null) {
				donationRequest.setInvoiceHeaderName(invoiceHeader.getCompanyFirstName()+" "+invoiceHeader.getCompanyLastName());
			}
			
			
			//Save Donation Details
			DonationDetails donationDetails = donationHelper.getDonationDetailsByReqObj(donationRequest);
			donationDetails = donationHelper.saveDonationDetails(donationDetails);

			// send sms
			SmsTemplateDetails smsTemplate = smsTemplateHelper.getSmsDetailsBySuperadminId(donationDetails.getSuperadminId(), SmsType.RECEIPT.name());
			if(smsTemplate != null && !donationRequest.getProgramName().equalsIgnoreCase("Sale")) {
				
				String messageBody = "Thank you for donating Rs. "+donationDetails.getAmount()+" at "+smsTemplate.getCompanyName()+". Click to download Receipt within 10 days. https://datafusionlab.co.in:8080/mycrm/donationinvoice/"+donationDetails.getReceiptNumber()+" "+smsTemplate.getCompanyRegards();
//              String messageBody = "Thank you for donating Rs. "+donationDetails.getAmount()+" at CEF INDIA. Click to download Receipt within 10 days. https://datafusionlab.co.in:8080/mycrm/donationinvoice/"+donationDetails.getReceiptNumber()+" CE FOUNDATION";

				String responce = smsHelper.sendSms(messageBody, smsTemplate, donationDetails);
			}
			
			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Register");
			return donationRequest;
//		}else {
//			donationRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
//			donationRequest.setRespMesg(Constant.INVALID_TOKEN);
//			return donationRequest; 
//		}
	}


	public List<DonationDetails> getDonationList(Request<DonationRequestObject> donationRequestObject) {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());
		
		List<DonationDetails> donationList = new ArrayList<>();
//		if (isValid) {
		if(donationRequest.getRoleType().equalsIgnoreCase(RoleType.SUPERADMIN.name())) {
			
			if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
				donationRequest.setFirstDate(todayDate);
				donationRequest.setLastDate(tomorrowDate);
				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
				donationRequest.setFirstDate(previousDate);
				donationRequest.setLastDate(todayDate);
				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
				donationRequest.setFirstDate(firstDateMonth);
				donationRequest.setLastDate(lastDateMonth);
				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.CUSTOM.name())) {
				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;
			}
			
		} else if(donationRequest.getRoleType().equalsIgnoreCase(RoleType.TEAM_LEADER.name())) {
			
			if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
				donationRequest.setFirstDate(todayDate);
				donationRequest.setLastDate(tomorrowDate);
				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
				donationRequest.setFirstDate(previousDate);
				donationRequest.setLastDate(todayDate);
				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
				donationRequest.setFirstDate(firstDateMonth);
				donationRequest.setLastDate(lastDateMonth);
				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.CUSTOM.name())) {
				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;
			}
		} else {
			
			if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
				donationRequest.setFirstDate(todayDate);
				donationRequest.setLastDate(tomorrowDate);
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
				donationRequest.setFirstDate(previousDate);
				donationRequest.setLastDate(todayDate);
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
				donationRequest.setFirstDate(firstDateMonth);
				donationRequest.setLastDate(lastDateMonth);
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;
				
			} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.CUSTOM.name())) {
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;
			}
		}	
//		}
		return donationList;
	}
	
	
	public List<DonationDetails> getDonationListByReceiptNumber(Request<DonationRequestObject> donationRequestObject) {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		
		List<DonationDetails> donationList = new ArrayList<>();
		donationList = donationHelper.getDonationListByReceiptNumber(donationRequest.getReceiptNumber());
		return donationList;
	}	


	public DonationRequestObject getCountAndSum(Request<DonationRequestObject> donationRequestObject) 
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();	
		donationHelper.validateDonationRequest(donationRequest);
		
//		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());
//		if (isValid) {

			//todays
			Object[] todays = donationHelper.getCountAndSum(donationRequest, todayDate, tomorrowDate);
			donationRequest.setTodaysCount((Long) todays[0]);
			donationRequest.setTodaysAmount((Double) todays[1]);
			
			//yesterday
			Object[] yesterday = donationHelper.getCountAndSum(donationRequest, previousDate, todayDate);
			donationRequest.setYesterdayCount((Long) yesterday[0]);
			donationRequest.setYesterdayAmount((Double) yesterday[1]);
			
			//monthly
			Object[] month = donationHelper.getCountAndSum(donationRequest, firstDateMonth, lastDateMonth);
			donationRequest.setMonthCount((Long) month[0]);
			donationRequest.setMonthAmount((Double) month[1]);
			
			//Active
			Long activeUserCount = userHelper.getActiveAndInactiveUserCount(donationRequest.getRoleType(), donationRequest.getCreatedBy(), Status.ACTIVE.name());
			donationRequest.setActiveUserCount(activeUserCount);
			
			//Inactive
			Long inactiveUserCount = userHelper.getActiveAndInactiveUserCount(donationRequest.getRoleType(), donationRequest.getCreatedBy(), Status.INACTIVE.name());
			donationRequest.setInactiveUserCount(inactiveUserCount);
					
			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Fetch");
			return donationRequest;
//		}else {
//			donationRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
//			donationRequest.setRespMesg(Constant.INVALID_TOKEN);
//			return donationRequest; 
//		}
	}


	public DonationRequestObject updateDonation(Request<DonationRequestObject> donationRequestObject)
		throws BizException, Exception {
			DonationRequestObject donationRequest = donationRequestObject.getPayload();
			donationHelper.validateDonationRequest(donationRequest);
			
			Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getLoginId(), donationRequest.getToken());
			logger.info("Add Donation. Is valid? : " + donationRequest.getLoginId() + " is " + isValid);
			
			
		return null;
	}


	public DonationRequestObject updateDonationStatus(Request<DonationRequestObject> donationRequestObject) throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);
		
		DonationDetails donationDetails = donationHelper.getDonationDetailsByIdAndSuperadminId(donationRequest.getId(), donationRequest.getSuperadminId());
		
		if(donationDetails != null) {
			donationDetails.setStatus(donationRequest.getStatus());
			donationHelper.updateDonationDetails(donationDetails);
			
			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Fetch");
			return donationRequest;
		}else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Donation Not Found");
			return donationRequest;
		}
	}

	
	

}

