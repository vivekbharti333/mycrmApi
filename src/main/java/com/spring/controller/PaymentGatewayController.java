package com.spring.controller;

import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spring.constant.Constant;
import com.spring.entities.CustomerDetails;
import com.spring.entities.DonationDetails;
import com.spring.entities.DonationType;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.entities.PaymentModeMaster;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.DonationTypeService;
import com.spring.services.PaymentGatewayService;
import com.spring.services.PaymentModeService;


@CrossOrigin(origins = "*")
@RestController
public class PaymentGatewayController {
	
	@Autowired
	PaymentGatewayService paymentGatewayService;
	
	
	@RequestMapping(path = "addPaymentGatewayDetails", method = RequestMethod.POST)
	public Response<PaymentRequestObject>addPaymentGatewayDetails(@RequestBody Request<PaymentRequestObject> paymentRequestObject, HttpServletRequest request)
	{
		GenricResponse<PaymentRequestObject> responseObj = new GenricResponse<PaymentRequestObject>();
		try {
			PaymentRequestObject responce =  paymentGatewayService.addPaymentGatewayDetails(paymentRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getPaymentGatewayDetailsList", method = RequestMethod.POST)
	public Response<PaymentGatewayDetails> getPaymentGatewayDetailsList(@RequestBody Request<PaymentRequestObject> paymentRequestObject) {
		GenricResponse<PaymentGatewayDetails> response = new GenricResponse<PaymentGatewayDetails>();
		try {
			List<PaymentGatewayDetails> paymentGatewayDetailsList = paymentGatewayService.getPaymentGatewayDetailsList(paymentRequestObject);
			return response.createListResponse(paymentGatewayDetailsList, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	

	 @RequestMapping(path = "receiveResponse", method = RequestMethod.POST)
	    public String receiveResponse(@RequestBody PaymentRequestObject paymentRequestObject) {

	        byte[] decodedBytes = Base64.getDecoder().decode( paymentRequestObject.getResponse());
	        String decodedString = new String(decodedBytes);
	      
	        JSONObject response = paymentGatewayService.updatePgResponseDetails(decodedString);
	        		
			
	        // Return a response
	        return "Response received successfully";
	    }
	
	
}
