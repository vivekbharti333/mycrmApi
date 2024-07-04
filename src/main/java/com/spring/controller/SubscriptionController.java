package com.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.constant.Constant;
import com.spring.exceptions.BizException;
import com.spring.object.request.Request;
import com.spring.object.request.SubscriptionRequestObject;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.SubscriptionService;

@CrossOrigin(origins = "*")
@RestController
public class SubscriptionController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private SubscriptionService subscriptionService;

	@RequestMapping(path = "buySubscription", method = RequestMethod.POST)
	public Response<SubscriptionRequestObject> buySubscription(@RequestBody Request<SubscriptionRequestObject> subscriptionRequestObject,
			HttpServletRequest request) {
		GenricResponse<SubscriptionRequestObject> responseObj = new GenricResponse<SubscriptionRequestObject>();
		try {
			SubscriptionRequestObject responce = subscriptionService.buySubscription(subscriptionRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	

}
