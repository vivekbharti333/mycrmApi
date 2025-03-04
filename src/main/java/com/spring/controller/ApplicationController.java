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
import com.spring.entities.ApplicationHeaderDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ApplicationRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.ApplicationService;


@CrossOrigin(origins = "*")
@RestController
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;
	
	
	@RequestMapping(path = "addUpdateApplicationHeader", method = RequestMethod.POST)
	public Response<ApplicationRequestObject>addUpdateApplicationHeader(@RequestBody Request<ApplicationRequestObject> applicationRequestObject, HttpServletRequest request)
	{
		GenricResponse<ApplicationRequestObject> responseObj = new GenricResponse<ApplicationRequestObject>();
		try {
			ApplicationRequestObject responce =  applicationService.addUpdateApplicationHeader(applicationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getApplicationHeaderDetailsBySuperadminId", method = RequestMethod.POST)
	public Response<ApplicationRequestObject>getApplicationHeaderDetailsBySuperadminId(@RequestBody Request<ApplicationRequestObject> applicationRequestObject, HttpServletRequest request)
	{
		GenricResponse<ApplicationRequestObject> responseObj = new GenricResponse<ApplicationRequestObject>();
		try {
			ApplicationRequestObject responce =  applicationService.getApplicationHeaderDetailsBySuperadminId(applicationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getApplicationDetailsByIpAddress", method = RequestMethod.POST)
	public Response<ApplicationRequestObject>getApplicationDetailsByIpAddress(@RequestBody Request<ApplicationRequestObject> applicationRequestObject, HttpServletRequest request)
	{
		GenricResponse<ApplicationRequestObject> responseObj = new GenricResponse<ApplicationRequestObject>();
		try {
			ApplicationRequestObject responce =  applicationService.getApplicationDetailsByIpAddress(applicationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getApplicationHeaderDetails", method = RequestMethod.POST)
	public Response<ApplicationHeaderDetails> getApplicationHeaderDetailsBySuperadminId(@RequestBody Request<ApplicationRequestObject> applicationRequestObject) {
		GenricResponse<ApplicationHeaderDetails> response = new GenricResponse<ApplicationHeaderDetails>();
		try {
			System.out.println("Ente rhai");
			List<ApplicationHeaderDetails> applicationHeaderDetails = applicationService.getApplicationHeaderDetails(applicationRequestObject);
			return response.createListResponse(applicationHeaderDetails, Constant.SUCCESS_CODE, String.valueOf(applicationHeaderDetails.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
}
