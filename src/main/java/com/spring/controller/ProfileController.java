package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.constant.Constant;
import com.spring.entities.SmsTemplateDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.ProfileService;
import com.spring.services.SmsTemplateService;

@CrossOrigin(origins = "*")
@RestController
public class ProfileController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private ProfileService profileService;

	@RequestMapping(path = "getLinkToRecharge", method = RequestMethod.POST)
	public Response<PaymentRequestObject> addSmsTemplate(@RequestBody Request<PaymentRequestObject> paymentRequestObject,
			HttpServletRequest request) {
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce = profileService.addSmsTemplate(paymentRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	

}
