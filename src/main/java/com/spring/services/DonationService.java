package com.spring.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.common.SendEmailHelper;
import com.spring.common.ShortUrl;
import com.spring.common.SmsHelper;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.EmailServiceDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.entities.SmsTemplateDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.PaymentMode;
import com.spring.enums.RequestFor;
import com.spring.enums.RoleType;
import com.spring.enums.SmsType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.EmailHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.helper.PaymentGatewayHelper;
import com.spring.helper.SmsTemplateHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;
import com.spring.paymentgateway.PhonePePaymentGateway;

@Service
public class DonationService {

	@Autowired
	private DonationHelper donationHelper;

	@Autowired
	private SmsTemplateHelper smsTemplateHelper;

	@Autowired
	private SmsHelper smsHelper;

	@Autowired
	private EmailHelper emailHelper;

	@Autowired
	private SendEmailHelper sendEmailHelper;

	@Autowired
	private PhonePePaymentGateway phonePePaymentGateway;

	@Autowired
	private UserHelper userHelper;

	@Autowired
	private InvoiceHelper invoiceHelper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ShortUrl shortUrl;

	@Autowired
	private PaymentGatewayHelper paymentGatewayHelper;

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

		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getLoginId(), donationRequest.getToken());
		
//		System.out.println("Created By : "+donationRequest.getCreatedBy());

		if (isValid) {
			
			System.out.println("Login id : "+donationRequest.getLoginId());
			System.out.println("Created by : "+donationRequest.getCreatedBy());
			
			if(donationRequest.getCreatedBy().equalsIgnoreCase("N/A")) {
				donationRequest.setCreatedBy(donationRequest.getLoginId());
			}

			// Validate Fields
			donationHelper.validateDonationRequestFields(donationRequest);

			InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderById(donationRequest.getInvoiceHeaderDetailsId());
			if (invoiceHeader != null) {
				donationRequest.setInvoiceHeaderName(invoiceHeader.getCompanyFirstName() + " " + invoiceHeader.getCompanyLastName());
			} else {
				donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				donationRequest.setRespMesg("Add invoice header first");
				return donationRequest;
			}

			// Get Team leader Details
			donationRequest = donationHelper.getTeamLeaderIdOfDonation(donationRequest);

			// Invoice Number Generate
			UserDetails teamLeaderCode = userHelper.getUserDetailsByLoginIdAndSuperadminId(
					donationRequest.getTeamLeaderId(), donationRequest.getSuperadminId());
			String currentYear = new SimpleDateFormat("MMyyyy").format(new Date());
			String invoiceNumber = teamLeaderCode.getUserCode() + "/" + invoiceHeader.getInvoiceInitial().toUpperCase() + "/" + currentYear + "/" + (invoiceHeader.getSerialNumber() + 1);

			donationRequest.setInvoiceNumber(invoiceNumber);

			// Generate Receipt Number
			String rendomNumber = userHelper.generateRandomChars("ABCD145pqrs678abcdef90EF9GHxyzIJKL5MNOPQRghijS1234560TUVWXYlmnoZ1234567tuvw890", 4);
			String receiptNumber = donationRequest.getSuperadminId().substring(0, 4) + rendomNumber + donationRequest.getMobileNumber().substring(7, 10);
			donationRequest.setReceiptNumber(receiptNumber);

			// payment gateway
			if (donationRequest.getPaymentMode().equalsIgnoreCase(PaymentMode.PAYMENT_GATEWAY.name())) {
				this.sendOnlinePaymentLink(donationRequest, invoiceHeader);
			} else {
				
				// Save Donation Details
				UserDetails userDetails = userHelper.getUserDetailsByLoginId(donationRequest.getCreatedBy());
				if(userDetails != null) {
					donationRequest.setCreatedbyName(userDetails.getFirstName()+" "+userDetails.getLastName());
				}
				DonationDetails donationDetails = donationHelper.getDonationDetailsByReqObj(donationRequest);
				donationDetails = donationHelper.saveDonationDetails(donationDetails);

				// increase serial number by 1
				invoiceHeader.setSerialNumber(invoiceHeader.getSerialNumber() + 1);
				invoiceHelper.updateInvoiceHeaderDetails(invoiceHeader);

				// send sms
				donationHelper.sendDonationInvoiceSms(donationDetails, invoiceHeader);

				// send email
				//donationHelper.sendDonationInvoiceEmail(donationDetails, invoiceHeader);
			}

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Register");
			return donationRequest;
		} else {
			donationRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			donationRequest.setRespMesg(Constant.INVALID_TOKEN);
			return donationRequest;
		}
	}
	
	public DonationRequestObject sendOnlinePaymentLink(DonationRequestObject donationRequest, InvoiceHeaderDetails invoiceHeader) throws BizException, Exception {
		
		PaymentGatewayDetails paymentGatewayDetails = paymentGatewayHelper
				.getPaymentGatewayDetailsBySuperadminId(donationRequest.getSuperadminId(), "PHONEPE");

		if (paymentGatewayDetails != null) {

			// Save Donation Details
			DonationDetails donationDetails = donationHelper.getDonationDetailsByReqObj(donationRequest);
			donationDetails = donationHelper.saveDonationDetails(donationDetails);

			DonationRequestObject paymentGatewayRequest = new DonationRequestObject();
			if (paymentGatewayDetails.getPgProvider().equalsIgnoreCase("PHONEPE")) {
				
				paymentGatewayRequest = phonePePaymentGateway.getPhonePePaymentLink(donationRequest, donationDetails, paymentGatewayDetails);

			} else if (paymentGatewayDetails.getPgProvider().equalsIgnoreCase("ROZARPAY")) {

			} else if (paymentGatewayDetails.getPgProvider().equalsIgnoreCase("PAYTM")) {

			}

			// Payment Gateway link SMS
			String paymentLink = shortUrl.shortUrl(paymentGatewayRequest.getPaymentGatewayPageRedirectUrl());
			donationHelper.sendDonationPaymentLinkSms(donationDetails, invoiceHeader, paymentGatewayRequest.getPaymentGatewayPageRedirectUrl());

		} else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Please add Payment Gatways Details first");
			return donationRequest;
		}
		
		
		return null;
	}

	public List<DonationDetails> getDonationList(Request<DonationRequestObject> donationRequestObject) {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());

		List<DonationDetails> donationList = new ArrayList<>();
//		if (isValid) {
		if (donationRequest.getRoleType().equalsIgnoreCase(RoleType.SUPERADMIN.name()) || donationRequest.getRoleType().equalsIgnoreCase(RoleType.DONOR_EXECUTIVE.name())) {

			if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
				donationRequest.setFirstDate(todayDate);
				donationRequest.setLastDate(tomorrowDate);
				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
				donationRequest.setFirstDate(previousDate);
				donationRequest.setLastDate(todayDate);
				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
				donationRequest.setFirstDate(firstDateMonth);
				donationRequest.setLastDate(lastDateMonth);
				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.CUSTOM.name())) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(donationRequest.getLastDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date nextDate = calendar.getTime();

				donationRequest.setLastDate(nextDate);

				donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
				return donationList;
			}

		} else if (donationRequest.getRoleType().equalsIgnoreCase(RoleType.TEAM_LEADER.name())) {

			if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
				donationRequest.setFirstDate(todayDate);
				donationRequest.setLastDate(tomorrowDate);
				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
				donationRequest.setFirstDate(previousDate);
				donationRequest.setLastDate(todayDate);
				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
				donationRequest.setFirstDate(firstDateMonth);
				donationRequest.setLastDate(lastDateMonth);
				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.CUSTOM.name())) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(donationRequest.getLastDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date nextDate = calendar.getTime();

				donationRequest.setLastDate(nextDate);

				donationList = donationHelper.getDonationListByTeamLeaderId(donationRequest);
				return donationList;
			}
		} else {

			if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
				donationRequest.setFirstDate(todayDate);
				donationRequest.setLastDate(tomorrowDate);
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
				donationRequest.setFirstDate(previousDate);
				donationRequest.setLastDate(todayDate);
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
				donationRequest.setFirstDate(firstDateMonth);
				donationRequest.setLastDate(lastDateMonth);
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;

			} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.CUSTOM.name())) {
				donationList = donationHelper.getDonationListCreatedBy(donationRequest);
				return donationList;
			}
		}
//		}
		return donationList;
	}

	public List<DonationDetails> getDonationListBySearchKey(Request<DonationRequestObject> donationRequestObject) {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();

		List<DonationDetails> donationList = new ArrayList<>();
		donationList = donationHelper.getDonationListBySearchKey(donationRequest);
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

		// todays
		Object[] todays = donationHelper.getCountAndSum(donationRequest, todayDate, tomorrowDate);
		donationRequest.setTodaysCount((Long) todays[0]);
		donationRequest.setTodaysAmount((Double) todays[1]);

		// yesterday
		Object[] yesterday = donationHelper.getCountAndSum(donationRequest, previousDate, todayDate);
		donationRequest.setYesterdayCount((Long) yesterday[0]);
		donationRequest.setYesterdayAmount((Double) yesterday[1]);

		// monthly
		Object[] month = donationHelper.getCountAndSum(donationRequest, firstDateMonth, lastDateMonth);
		donationRequest.setMonthCount((Long) month[0]);
		donationRequest.setMonthAmount((Double) month[1]);

		// Active
		Long activeUserCount = userHelper.getActiveAndInactiveUserCount(donationRequest.getRoleType(), donationRequest.getCreatedBy(), Status.ACTIVE.name());
		donationRequest.setActiveUserCount(activeUserCount);

		// Inactive
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

//			if(isValid) {
		DonationDetails donationDetails = donationHelper.getDonationDetailsByIdAndSuperadminId(donationRequest.getId(),
				donationRequest.getSuperadminId());
		if (donationDetails != null) {
			donationDetails = donationHelper.getUpdatedDonationDetailsByReqObj(donationRequest, donationDetails);
			donationDetails = donationHelper.updateDonationDetails(donationDetails);

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Updated");
			return donationRequest;
		} else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Invalid Id");
			return donationRequest;
		}
//			} else {
//				//session out
//			}

	}

	public DonationRequestObject updateDonationStatus(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);

		DonationDetails donationDetails = donationHelper.getDonationDetailsByIdAndSuperadminId(donationRequest.getId(),
				donationRequest.getSuperadminId());

		if (donationDetails != null) {
			donationDetails.setStatus(donationRequest.getStatus());
			donationHelper.updateDonationDetails(donationDetails);

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Fetch");
			return donationRequest;
		} else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Donation Not Found");
			return donationRequest;
		}
	}
	
	public List<DonationDetails> getStartPerformer(Request<DonationRequestObject> donationRequestObject)
			throws BizException {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);
		
		donationRequest.setRequestedFor(RequestFor.ALL.name());
		List<DonationDetails> donationList = new ArrayList<>();

		donationList = donationHelper.getStartPerformer(donationRequest);
		return donationList;
	}
	
	public List<DonationDetails> getStartTeam(Request<DonationRequestObject> donationRequestObject) 
			throws BizException {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);

		donationRequest.setRequestedFor(RequestFor.TEAM.name());
		List<DonationDetails> donationList = new ArrayList<>();

		donationList = donationHelper.getStartPerformer(donationRequest);
		return donationList;
	}

	public List<DonationDetails> getDonationCountAndAmountGroupByName(
			Request<DonationRequestObject> donationRequestObject) throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);

		List<DonationDetails> donationList = new ArrayList<>();

		if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
			donationList = donationHelper.getDonationCountAndAmountGroupByName(donationRequest, todayDate, tomorrowDate);
			return donationList;

		} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
			donationList = donationHelper.getDonationCountAndAmountGroupByName(donationRequest, previousDate, todayDate);
			return donationList;

		} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
			donationList = donationHelper.getDonationCountAndAmountGroupByName(donationRequest, firstDateMonth, lastDateMonth);
			return donationList;
		}
		return donationList;

	}

	public List<DonationDetails> getDonationPaymentModeCountAndAmountGroupByName(
			Request<DonationRequestObject> donationRequestObject) throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);

		List<DonationDetails> donationList = new ArrayList<>();

		if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
			donationList = donationHelper.getDonationPaymentModeCountAndAmountGroupByName(donationRequest, todayDate, tomorrowDate);
			return donationList;

		} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
			donationList = donationHelper.getDonationPaymentModeCountAndAmountGroupByName(donationRequest, previousDate, todayDate);
			return donationList;

		} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
			donationList = donationHelper.getDonationPaymentModeCountAndAmountGroupByName(donationRequest, firstDateMonth, lastDateMonth);
			return donationList;
		}
		return donationList;

	}

	public List<DonationDetails> getDonationProgramNameCountAndAmountGroupByName(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);

		List<DonationDetails> donationList = new ArrayList<>();

		if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.TODAY.name())) {
			donationList = donationHelper.getDonationProgramNameCountAndAmountGroupByName(donationRequest, todayDate, tomorrowDate);
			return donationList;

		} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.YESTERDAY.name())) {
			donationList = donationHelper.getDonationProgramNameCountAndAmountGroupByName(donationRequest, previousDate, todayDate);
			return donationList;

		} else if (donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.MONTH.name())) {
			donationList = donationHelper.getDonationProgramNameCountAndAmountGroupByName(donationRequest, firstDateMonth, lastDateMonth);
			return donationList;
		}
		return donationList;
	}

	



}
