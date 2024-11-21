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
import com.spring.entities.CurrencyMaster;
import com.spring.exceptions.BizException;
import com.spring.object.request.CurrencyRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.CurrencyService;


@CrossOrigin(origins = "*")
@RestController
public class CurrencyController {
	
	@Autowired
	private CurrencyService currencyService;
	
	
	@RequestMapping(path = "addUpdateCurrencyMaster", method = RequestMethod.POST)
	public Response<CurrencyRequestObject>addUpdateCurrencyMaster(@RequestBody Request<CurrencyRequestObject> currencyRequestObject, HttpServletRequest request)
	{
		GenricResponse<CurrencyRequestObject> responseObj = new GenricResponse<CurrencyRequestObject>();
		try {
			CurrencyRequestObject responce =  currencyService.addUpdateCurrencyMaster(currencyRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	
	@RequestMapping(path = "getCurrencyMaster", method = RequestMethod.POST)
	public Response<CurrencyMaster> getCurrencyDetails(@RequestBody Request<CurrencyRequestObject> donationRequestObject) {
		GenricResponse<CurrencyMaster> response = new GenricResponse<CurrencyMaster>();
		try {
			List<CurrencyMaster> currencyList = currencyService.getCurrencyDetails(donationRequestObject);
			return response.createListResponse(currencyList, Constant.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "addUpdateCurrencyBySuperadmin", method = RequestMethod.POST)
	public Response<CurrencyRequestObject>addUpdateCurrencyBySuperadmin(@RequestBody Request<CurrencyRequestObject> currencyRequestObject, HttpServletRequest request)
	{
		GenricResponse<CurrencyRequestObject> responseObj = new GenricResponse<CurrencyRequestObject>();
		try {
			CurrencyRequestObject responce =  currencyService.addUpdateCurrencyBySuperadmin(currencyRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getCurrencyDetailsBySuperadmin", method = RequestMethod.POST)
	public Response<CurrencyMaster> getCurrencyDetailsBySuperadmin(@RequestBody Request<CurrencyRequestObject> donationRequestObject) {
		GenricResponse<CurrencyMaster> response = new GenricResponse<CurrencyMaster>();
		try {
			List<CurrencyMaster> currencyList = currencyService.getCurrencyDetailsBySuperadmin(donationRequestObject);
			return response.createListResponse(currencyList, Constant.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	
}
