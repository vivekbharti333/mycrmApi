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
import com.ngo.entities.PaymentGatewayDetails;
import com.ngo.object.request.PaymentRequestObject;
import com.ngo.services.PaymentGatewayService;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;

@CrossOrigin(origins = "*")
@RestController
public class PaymentGatewayController {

	@Autowired
	private PaymentGatewayService paymentGatewayService;

	@RequestMapping(path = "addUpdatePaymentGatewayDetails", method = RequestMethod.POST)
	public Response<PaymentRequestObject> addUpdatePaymentGatewayDetails(
			@RequestBody Request<PaymentRequestObject> paymentRequestObject, HttpServletRequest request) {
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce = paymentGatewayService.addUpdatePaymentGatewayDetails(paymentRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}

	@RequestMapping(path = "getPaymentGatewayDetailsList", method = RequestMethod.POST)
	public Response<PaymentGatewayDetails> getPaymentGatewayDetailsList(
			@RequestBody Request<PaymentRequestObject> paymentRequestObject) {
		GenricResponse<PaymentGatewayDetails> response = new GenricResponse<PaymentGatewayDetails>();
		try {
			List<PaymentGatewayDetails> paymentGatewayDetailsList = paymentGatewayService
					.getPaymentGatewayDetailsList(paymentRequestObject);
			return response.createListResponse(paymentGatewayDetailsList, 200,
					String.valueOf(paymentGatewayDetailsList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}

	@RequestMapping(path = "changePaymentGatewayStatus", method = RequestMethod.POST)
	public Response<PaymentRequestObject> changePaymentGatewayStatus(
			@RequestBody Request<PaymentRequestObject> paymentRequestObject, HttpServletRequest request) {
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce = paymentGatewayService.changePaymentGatewayStatus(paymentRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}

}
