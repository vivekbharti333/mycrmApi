package com.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.spring.common.PdfInvoice;
import com.spring.common.SendEmailHelper;
import com.spring.constant.Constant;
import com.spring.email.NimbusEmail;
import com.spring.email.ZeptoEmail;
import com.spring.entities.AddressDetails;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.UserDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.EmailHelper;
import com.spring.helper.FaceRecognitionHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.helper.SubscriptionHelper;
import com.spring.helper.TestHelper;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.UserRequestObject;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.paymentgateway.PhonePePaymentGateway;
import com.spring.pdf.ItextPdfReceipt;
import com.spring.services.AttendenceService;
import com.spring.services.UserService;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;


@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private	UserService userService;
	
	@Autowired
	private FaceRecognitionHelper faceRecognitionHelper;
	
	@Autowired
	private NimbusEmail nimbusEmail;

	
	@Autowired
	public HttpServletRequest request;
	
    
    @Autowired 
    private InvoiceHelper invoiceHelper;
    
    @Autowired 
    private DonationHelper donationHelper;
    
    @Autowired
    private PdfInvoice pdfInvoice;
    
    @Autowired
    private TestHelper testHelper;
    
    @Autowired
    private EmailHelper emailHelper;
    
    @Autowired
    private ItextPdfReceipt itextPdfReceipt;
    
    @Autowired
    private ZeptoEmail zeptoEmail;
     
	
	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletResponse response) throws IOException, MessagingException {
		InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderById(1L);
		
		zeptoEmail.sendEmailWithAttachments();
		
//		itextPdfReceipt.createReceipt();
		
//		DonationDetails donationDetails = donationHelper.getDonationDetailsByIdAndSuperadminId(999L, "");
//		
//		ByteArrayOutputStream pdfContent = pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeader);
//		
//		System.out.println("hujhj : "+pdfContent);
		
//		nimbusEmail.sendNimbusEmail(donationDetails);
		
		
		
//		pdfThankYouLatter.pdf();
		

		return new ModelAndView("home");
	}
	
	
	@RequestMapping(value = "version")
	public String version(HttpServletResponse response) throws Exception {
		
		DonationRequestObject donationRequest = new DonationRequestObject();
		
		donationRequest.setSuperadminId("6289639160");
		donationRequest.setRequestedFor("YESTERDAY");
		donationRequest.setRoleType("SUPERADMIN");
		donationRequest.setCreatedBy("6289639160");
		
		emailHelper.sendeMail();
//		emailHelper.SMTPBrevoEmailSender ();
		
//		testHelper.checkIt(donationRequest);
//		faceRecognitionHelper.compareFace();
		
		return "1.3";
	}
	

//	@Scheduled(fixedDelay = 5000)
	@RequestMapping(path = "test", method = RequestMethod.GET)
	public String test() throws Exception {

		String clientIp = request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For")
				: request.getRemoteAddr();

//		String param = phonePePaymentGateway.getPaymetGatewayParam();
//		phonePePaymentGateway.paymentPageTest(param);

//		attendenceService.amazonApi();

		return "Working : " + clientIp;
	}

	@RequestMapping(path = "updateUserSubscription", method = RequestMethod.POST)
	public Response<UserRequestObject> updateUserSubscription(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject response = userService.updateUserSubscription(userRequestObject);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "doLogin", method = RequestMethod.POST)
	public Response<UserRequestObject> doLogin(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject response = userService.doLogin(userRequestObject);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getUserDetailsByLoginId", method = RequestMethod.POST)
	public Response<UserRequestObject> getUserDetailsByLoginId(@RequestBody Request<UserRequestObject> userRequestObject, HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			
			UserRequestObject response = userService.getUserDetailsByLoginId(userRequestObject);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}

	@RequestMapping(path = "userRegistration", method = RequestMethod.POST)
	public Response<UserRequestObject> userRegistration(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject responce = userService.userRegistration(userRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}

	@RequestMapping(path = "updateUserDetails", method = RequestMethod.POST)
	public Response<UserRequestObject> updateUserDetails(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject responce = userService.updateUserDetails(userRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "changeUserPassword", method = RequestMethod.POST)
	public Response<UserRequestObject> changeUserPassword(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject responce = userService.changeUserPassword(userRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}

	@RequestMapping(path = "changeUserStatus", method = RequestMethod.POST)
	public Response<UserRequestObject> changeUserStatus(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject responce = userService.changeUserStatus(userRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "removeUserParmanent", method = RequestMethod.POST)
	public Response<UserRequestObject> removeUserParmanent(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject responce = userService.removeUserParmanent(userRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "changeTeamLeader", method = RequestMethod.POST)
	public Response<UserRequestObject> changeTeamLeader(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject responce = userService.changeTeamLeader(userRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "changeUserRole", method = RequestMethod.POST)
	public Response<UserRequestObject> changeUserRole(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
		GenricResponse<UserRequestObject> responseObj = new GenricResponse<UserRequestObject>();
		try {
			UserRequestObject responce = userService.changeUserRole(userRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	

	@RequestMapping(path = "getUserDetails", method = RequestMethod.POST)
	public Response<UserDetails> getUserDetails(@RequestBody Request<UserRequestObject> userRequestObject) {
		GenricResponse<UserDetails> response = new GenricResponse<UserDetails>();
		try {
			List<UserDetails> userList = userService.getUserDetails(userRequestObject);
			return response.createListResponse(userList, 200, String.valueOf(userList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "getUserDetailsByUserRole", method = RequestMethod.POST)
	public Response<UserDetails> getUserDetailsByUserRole(@RequestBody Request<UserRequestObject> userRequestObject) {
		GenricResponse<UserDetails> response = new GenricResponse<UserDetails>();
		try {
			List<UserDetails> userList = userService.getUserDetailsByUserRole(userRequestObject);
			return response.createListResponse(userList, 200, String.valueOf(userList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getFundRisingOfficersBySuperadminId", method = RequestMethod.POST)
	public Response<UserDetails> getFundRisingOfficersBySuperadminId(@RequestBody Request<UserRequestObject> userRequestObject) {
		GenricResponse<UserDetails> response = new GenricResponse<UserDetails>();
		try {
			List<UserDetails> userList = userService.getFundRisingOfficersBySuperadminId(userRequestObject);
			return response.createListResponse(userList, 200, String.valueOf(userList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "getUserListForDropDown", method = RequestMethod.POST)
	public Response<UserDetails> getUserListForDropDown(@RequestBody Request<UserRequestObject> userRequestObject) {
		GenricResponse<UserDetails> response = new GenricResponse<UserDetails>();
		try {
			List<UserDetails> userList = userService.getUserListForDropDown(userRequestObject);
			return response.createListResponse(userList, 200, String.valueOf(userList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getAddressDetails", method = RequestMethod.POST)
	public Response<AddressDetails> getAddressDetails(@RequestBody Request<UserRequestObject> userRequestObject) {
		GenricResponse<AddressDetails> response = new GenricResponse<AddressDetails>();
		try {
			List<AddressDetails> addressList = userService.getAddressDetails(userRequestObject);
			return response.createListResponse(addressList, 200, String.valueOf(addressList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
}
