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
import com.spring.entities.CustomerDetails;
import com.spring.entities.DonationType;
import com.spring.entities.PaymentModeMaster;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.DonationTypeService;
import com.spring.services.PaymentModeService;


@CrossOrigin(origins = "*")
@RestController
public class PaymentModeController {
	
	@Autowired
	PaymentModeService optionTypeService;
	
	
	@RequestMapping(path = "addPaymentModeMaster", method = RequestMethod.POST)
	public Response<PaymentRequestObject>addPaymentModeMaster(@RequestBody Request<PaymentRequestObject> optionRequestObject, HttpServletRequest request)
	{
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce =  optionTypeService.addPaymentModeMaster(optionRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getPaymentModeList", method = RequestMethod.POST)
	public Response<PaymentModeMaster> getOptionTypeDetailsList(@RequestBody Request<PaymentRequestObject> optionRequestObject) {
		GenricResponse<PaymentModeMaster> response = new GenricResponse<PaymentModeMaster>();
		try {
			List<PaymentModeMaster> optionList = optionTypeService.getPaymentModeListBySuperadminId(optionRequestObject);
			return response.createListResponse(optionList, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	
	@RequestMapping(path = "addPaymentModeBySuperadmin", method = RequestMethod.POST)
	public Response<PaymentRequestObject>addPaymentModeBySuperadmin(@RequestBody Request<PaymentRequestObject> optionRequestObject, HttpServletRequest request)
	{
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce =  optionTypeService.addPaymentModeBySuperadmin(optionRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "getPaymentModeListBySuperadminId", method = RequestMethod.POST)
	public Response<PaymentModeMaster> getPaymentModeListBySuperadminId(@RequestBody Request<PaymentRequestObject> optionRequestObject) {
		GenricResponse<PaymentModeMaster> response = new GenricResponse<PaymentModeMaster>();
		try {
			List<PaymentModeMaster> optionList = optionTypeService.getPaymentModeListBySuperadminId(optionRequestObject);
			return response.createListResponse(optionList, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	
}
