package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.constant.Constant;
import com.spring.entities.EmailServiceDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.EmailServiceRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.EmailService;


@CrossOrigin(origins = "*")
@RestController
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	
	@RequestMapping(path = "addEmailServiceDetails", method = RequestMethod.POST)
	public Response<EmailServiceRequestObject>addEmailServiceDetails(@RequestBody Request<EmailServiceRequestObject> emailServiceRequestObject, HttpServletRequest request)
	{
		GenricResponse<EmailServiceRequestObject> responseObj = new GenricResponse<EmailServiceRequestObject>();
		try {
			EmailServiceRequestObject responce =  emailService.addEmailServiceDetails(emailServiceRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "updateEmailServiceDetails", method = RequestMethod.POST)
	public Response<EmailServiceRequestObject>updateEmailServiceDetails(@RequestBody Request<EmailServiceRequestObject> emailServiceRequestObject, HttpServletRequest request)
	{
		GenricResponse<EmailServiceRequestObject> responseObj = new GenricResponse<EmailServiceRequestObject>();
		try {
			EmailServiceRequestObject responce =  emailService.updateEmailServiceDetails(emailServiceRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getEmailServiceDetailsList", method = RequestMethod.POST)
	public Response<EmailServiceDetails> getEmailServiceDetailsList(@RequestBody Request<EmailServiceRequestObject> optionRequestObject) {
		GenricResponse<EmailServiceDetails> response = new GenricResponse<EmailServiceDetails>();
		try {
			List<EmailServiceDetails> emailServiceDetailsList = emailService.getEmailServiceDetailsList(optionRequestObject);
			return response.createListResponse(emailServiceDetailsList, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	

	
}
