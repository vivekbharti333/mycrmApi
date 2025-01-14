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
import com.spring.entities.LeadDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.LeadRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.LeadDetailsService;

@CrossOrigin(origins = "*")
@RestController
public class LeadDetailsController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private LeadDetailsService leadDetailsService;

	
	@RequestMapping(path = "createLead", method = RequestMethod.POST)
	public Response<LeadRequestObject>createEnquiry(@RequestBody Request<LeadRequestObject> leadRequestObject, HttpServletRequest request) {
		
		GenricResponse<LeadRequestObject> responseObj = new GenricResponse<LeadRequestObject>();
		try {
			LeadRequestObject responce = leadDetailsService.createLead(leadRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "updateLead", method = RequestMethod.POST)
	public Response<LeadRequestObject>updateLead(@RequestBody Request<LeadRequestObject> leadRequestObject, HttpServletRequest request) {
		
		GenricResponse<LeadRequestObject> responseObj = new GenricResponse<LeadRequestObject>();
		try {
			LeadRequestObject responce = leadDetailsService.updateLead(leadRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getLeadCountByStatus", method = RequestMethod.POST)
	public Response<LeadRequestObject>getLeadCountByStatus(@RequestBody Request<LeadRequestObject> leadRequestObject, HttpServletRequest request) {
		
		GenricResponse<LeadRequestObject> responseObj = new GenricResponse<LeadRequestObject>();
		try {
			LeadRequestObject responce = leadDetailsService.getLeadCountByStatus(leadRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "getLeadList", method = RequestMethod.POST)
	public Response<LeadDetails> getLeadList(@RequestBody Request<LeadRequestObject> leadRequestObject) {
		GenricResponse<LeadDetails> response = new GenricResponse<LeadDetails>();
		try {
			List<LeadDetails> enquiryList = leadDetailsService.getLeadList(leadRequestObject);
			return response.createListResponse(enquiryList, 200, String.valueOf(enquiryList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getLeadListByStatus", method = RequestMethod.POST)
	public Response<LeadDetails> getLeadListByStatus(@RequestBody Request<LeadRequestObject> leadRequestObject) {
		GenricResponse<LeadDetails> response = new GenricResponse<LeadDetails>();
		try {
			List<LeadDetails> enquiryList = leadDetailsService.getLeadListByStatus(leadRequestObject);
			return response.createListResponse(enquiryList, 200, String.valueOf(enquiryList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getFollowupLeadList", method = RequestMethod.POST)
	public Response<LeadDetails> getFollowupLeadList(@RequestBody Request<LeadRequestObject> leadRequestObject) {
		GenricResponse<LeadDetails> response = new GenricResponse<LeadDetails>();
		try {
			List<LeadDetails> enquiryList = leadDetailsService.getFollowupLeadList(leadRequestObject);
			return response.createListResponse(enquiryList, 200, String.valueOf(enquiryList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
}
