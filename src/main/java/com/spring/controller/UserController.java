package com.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
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
import com.spring.common.PdfThankYouLatter;
import com.spring.common.SendEmailHelper;
import com.spring.constant.Constant;
import com.spring.entities.AddressDetails;
import com.spring.entities.UserDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.FaceRecognitionHelper;
import com.spring.object.request.Request;
import com.spring.object.request.UserRequestObject;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.paymentgateway.PhonePePaymentGateway;
import com.spring.services.AttendenceService;
import com.spring.services.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	UserService userService;
	
	@Autowired
	PdfInvoice pdfInvoice;
	
	@Autowired
	SendEmailHelper sendEmailHelper;
	
	@Autowired
	private PdfThankYouLatter pdfThankYouLatter;
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private PhonePePaymentGateway phonePePaymentGateway;
	
	@Autowired
	private AttendenceService attendenceService;
	
	@Autowired
	private FaceRecognitionHelper faceRecognitionHelper;

	
	@Autowired
	public HttpServletRequest request;
	
	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletResponse response) throws IOException {
		
//		pdfThankYouLatter.pdf();
		return new ModelAndView("home");
	}
	
	
	@RequestMapping(value = "version")
	public String version(HttpServletResponse response) throws Exception {
		
		faceRecognitionHelper.compareFace();
		
		return "1.2";
	}
	
	@RequestMapping(value = "rozarpay")
	public String rozarpay() throws Exception {

		RazorpayClient razorpay = new RazorpayClient("FOm6kecVJSX7lp", "rzp_live_nGALlLllOEWX3I");
		JSONObject paymentLinkRequest = new JSONObject();
		paymentLinkRequest.put("upi_link", true);
		paymentLinkRequest.put("amount", 1000);
		paymentLinkRequest.put("currency", "INR");
		paymentLinkRequest.put("accept_partial", false);
		paymentLinkRequest.put("first_min_partial_amount", 100);
		paymentLinkRequest.put("description", "Payment for policy no #23456");
		JSONObject customer = new JSONObject();
		customer.put("name", "+919000090000");
		customer.put("contact", "Gaurav Kumar");
		customer.put("email", "gaurav.kumar@example.com");
		paymentLinkRequest.put("customer", customer);
		JSONObject notify = new JSONObject();
		notify.put("sms", true);
		notify.put("email", true);
		paymentLinkRequest.put("notify", notify);
		paymentLinkRequest.put("reminder_enable", true);
		JSONObject notes = new JSONObject();
		notes.put("policy_name", "Jeevan Bima");
		paymentLinkRequest.put("notes", notes);

		PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
		
		System.out.println("Payment : "+payment);
		return null;
	}

//	@Scheduled(fixedDelay = 5000)
	@RequestMapping(path = "test", method = RequestMethod.GET)
	public String test() throws Exception {
		
		String clientIp = request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For") : request.getRemoteAddr();
		
//		DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo("123456789");
//		System.out.println(donationDetails+" khjhjkhjhj");
//		if(donationDetails != null) {
//			sendEmailHelper.sendEmailWithInvoice(donationDetails);
//		}
		
//		String param = phonePePaymentGateway.getPaymetGatewayParam();
//		phonePePaymentGateway.paymentPageTest(param);
		 
		String jsonResponse = "{\"success\":true,\"code\":\"PAYMENT_INITIATED\",\"message\":\"Payment initiated\",\"data\":{\"merchantId\":\"M22XLI1BBSR4N\",\"merchantTransactionId\":\"CI/CEF/022024/4697\",\"instrumentResponse\":{\"type\":\"PAY_PAGE\",\"redirectInfo\":{\"url\":\"https://mercury-t2.phonepe.com/transact/pg?token=NGMzYzdhZDM5ODkwMWNiM2U0OTc4NmY2MGVhMDU2N2Y5NzM0M2I1MTJkYmZiNDc3MDVhNDYwNjdjNzY3YTc5YjFlNGNkOTkyZTlmYTZhZmRhZjZjYjczOThjYTQ1ODM1OjQ2NWNlYmE3YjIxZDJjNDM3NmMzNWYxMTMxYjdjNDdm\",\"method\":\"GET\"}}}}";
		
		JSONObject jsonObject = new JSONObject(jsonResponse);
		String code = jsonObject.getString("code");
        boolean success = jsonObject.getBoolean("success");

        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject instrumentResponse = data.getJSONObject("instrumentResponse");
        JSONObject redirectInfo = instrumentResponse.getJSONObject("redirectInfo");
        String paymentUrl = redirectInfo.getString("url");    
	     System.out.println("code : "+code);
	     System.out.println("success : "+success);
	     System.out.println("data : "+data);
	     System.out.println("instrumentResponse : "+instrumentResponse);
	     System.out.println("url : "+paymentUrl);
	     
		
//		attendenceService.amazonApi();
		
		
		
//		 String endpoint = "http://tinyurl.com/api-create.php?url=" + URLEncoder.encode(paymentUrl, "UTF-8");
//	        URL url = new URL(endpoint);
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//	        conn.setRequestMethod("GET");
//
//	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	        String shortUrl = reader.readLine();
//
//	        reader.close();
//	        conn.disconnect();
//
//	        System.out.println("hgh : "+shortUrl); 
		
		
	return "Working : "+clientIp;
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
			return response.createListResponse(userList, 200);
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
			return response.createListResponse(userList, 200);
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
			return response.createListResponse(userList, 200);
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
			return response.createListResponse(addressList, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
}
