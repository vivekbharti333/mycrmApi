package com.spring.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import com.spring.common.PdfInvoice;
import com.spring.common.SmsHelper;
import com.spring.constant.Constant;
import com.spring.entities.AddressDetails;
import com.spring.entities.UserDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.Request;
import com.spring.object.request.UserRequestObject;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
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
	private SmsHelper smsHelper;

	
	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletResponse response) throws IOException {
//		String resp =smsHelper.sendSms();
//		System.out.println("Res : "+resp);
		
//		System.out.println("Resp : "+resp);
		
		String currentYear = new SimpleDateFormat("MMyyyy").format(new Date());
		
		System.out.println(currentYear);
		
		return new ModelAndView("home");
	}

//	@Scheduled(fixedDelay = 5000)
	@RequestMapping(path = "test", method = RequestMethod.GET)
	public String hi() throws Exception {
		
//		pdfInvoice.htmlContaints();
		return "Working";
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
	public Response<UserRequestObject> getUserDetailsByLoginId(@RequestBody Request<UserRequestObject> userRequestObject,
			HttpServletRequest request) {
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

	@RequestMapping(path = "getUserDetails", method = RequestMethod.POST)
	public Response<UserDetails> getAllUser(@RequestBody Request<UserRequestObject> userRequestObject) {
		GenricResponse<UserDetails> response = new GenricResponse<UserDetails>();
		try {
			List<UserDetails> userList = userService.getUserDetails(userRequestObject);
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
