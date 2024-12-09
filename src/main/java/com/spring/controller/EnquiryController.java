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
import com.spring.entities.EnquiryDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.EnquiryRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.EnquiryService;

@CrossOrigin(origins = "*")
@RestController
public class EnquiryController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private EnquiryService enquiryService;

	
	@RequestMapping(path = "createEnquiry", method = RequestMethod.POST)
	public Response<EnquiryRequestObject>createEnquiry(@RequestBody Request<EnquiryRequestObject> enquiryRequestObject, HttpServletRequest request) {
		
		GenricResponse<EnquiryRequestObject> responseObj = new GenricResponse<EnquiryRequestObject>();
		try {
			EnquiryRequestObject responce = enquiryService.createEnquiry(enquiryRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	


	@RequestMapping(path = "getEnquiryList", method = RequestMethod.POST)
	public Response<EnquiryDetails> getCustomerList(@RequestBody Request<EnquiryRequestObject> enquiryRequestObject) {
		GenricResponse<EnquiryDetails> response = new GenricResponse<EnquiryDetails>();
		try {
			List<EnquiryDetails> enquiryList = enquiryService.getEnquiryList(enquiryRequestObject);
			return response.createListResponse(enquiryList, 200, String.valueOf(enquiryList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
}
