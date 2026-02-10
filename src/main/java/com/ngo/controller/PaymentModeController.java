package com.ngo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.ngo.entities.PaymentModeMaster;
import com.ngo.object.request.PaymentRequestObject;
import com.ngo.services.PaymentModeService;


@CrossOrigin(origins = "*")
@RestController
public class PaymentModeController {
	
	@Autowired
	PaymentModeService paymentModeService;
	
	
	@RequestMapping(path = "addPaymentModeMaster", method = RequestMethod.POST)
	public Response<PaymentRequestObject>addPaymentModeMaster(@RequestBody Request<PaymentRequestObject> optionRequestObject, HttpServletRequest request)
	{
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce =  paymentModeService.addPaymentModeMaster(optionRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getMasterPaymentModeList", method = RequestMethod.POST)
	public Response<PaymentModeMaster> getMasterPaymentModeList(@RequestBody Request<PaymentRequestObject> paymentRequestObject) {
		GenricResponse<PaymentModeMaster> response = new GenricResponse<PaymentModeMaster>();
		try {
			List<PaymentModeMaster> optionList = paymentModeService.getMasterPaymentModeList(paymentRequestObject);
			return response.createListResponse(optionList, 200, String.valueOf(optionList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	
	@RequestMapping(path = "changeStatusOfPaymentModeMaster", method = RequestMethod.POST)
	public Response<PaymentRequestObject>changeStatusOfPaymentModeMaster(@RequestBody Request<PaymentRequestObject> paymentRequestObject, HttpServletRequest request)
	{
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce =  paymentModeService.changeStatusOfPaymentModeMaster(paymentRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "addOrUpdatePaymentModeBySuperadmin", method = RequestMethod.POST)
	public Response<PaymentRequestObject>addPaymentModeBySuperadmin(@RequestBody Request<PaymentRequestObject> optionRequestObject, HttpServletRequest request)
	{
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce =  paymentModeService.addPaymentModeBySuperadmin(optionRequestObject);
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
			List<PaymentModeMaster> optionList = paymentModeService.getPaymentModeListBySuperadminId(optionRequestObject);
			return response.createListResponse(optionList, 200, String.valueOf(optionList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	
}
