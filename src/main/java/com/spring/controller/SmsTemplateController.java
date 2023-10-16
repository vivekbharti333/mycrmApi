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
import com.spring.object.request.Request;
import com.spring.object.request.SmsTemplateRequestObject;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.SmsTemplateService;

@CrossOrigin(origins = "*")
@RestController
public class SmsTemplateController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	SmsTemplateService smsTemplateService;

	@RequestMapping(path = "addSmsTemplate", method = RequestMethod.POST)
	public Response<SmsTemplateRequestObject> addSmsTemplate(@RequestBody Request<SmsTemplateRequestObject> smsTemplateRequestObject,
			HttpServletRequest request) {
		GenricResponse<SmsTemplateRequestObject> responseObj = new GenricResponse<SmsTemplateRequestObject>();
		try {
			SmsTemplateRequestObject responce = smsTemplateService.addSmsTemplate(smsTemplateRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "updateSmsTemplate", method = RequestMethod.POST)
	public Response<SmsTemplateRequestObject> updateSmsTemplate(@RequestBody Request<SmsTemplateRequestObject> smsTemplateRequestObject,
			HttpServletRequest request) {
		GenricResponse<SmsTemplateRequestObject> responseObj = new GenricResponse<SmsTemplateRequestObject>();
		try {
			SmsTemplateRequestObject responce = smsTemplateService.updateSmsTemplate(smsTemplateRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}



	@RequestMapping(path = "getSmsTemplateList", method = RequestMethod.POST)
	public Response<SmsTemplateDetails> getSmsTemplateList(@RequestBody Request<SmsTemplateRequestObject> smsTemplateRequestObject) {
		GenricResponse<SmsTemplateDetails> response = new GenricResponse<SmsTemplateDetails>();
		try {
			List<SmsTemplateDetails> smsTemplateList = smsTemplateService.getSmsTemplateList(smsTemplateRequestObject);
			return response.createListResponse(smsTemplateList, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
}
