package com.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.PaymentGatewayHelper;
import com.spring.helper.PhonePePgHelper;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;

@Service
public class PaymentGatewayService {

	@Autowired
	private PaymentGatewayHelper paymentGatewayHelper;
	
	@Autowired
	private PhonePePgHelper phonePePgHelper;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public PaymentRequestObject addPaymentGatewayDetails(Request<PaymentRequestObject> paymentRequestObject)
			throws BizException, Exception {
		PaymentRequestObject paymentGatewayRequest = paymentRequestObject.getPayload();
		paymentGatewayHelper.validatePaymentModeRequest(paymentGatewayRequest);

		PaymentGatewayDetails existsPaymentGateway = paymentGatewayHelper.getPaymentGatewayDetailsBySuperadminId(
				paymentGatewayRequest.getSuperadminId(), paymentGatewayRequest.getPgProvider());
		if (existsPaymentGateway == null) {
			PaymentGatewayDetails paymentGatewayDetails = paymentGatewayHelper
					.getPaymentGatewayByReqObj(paymentGatewayRequest);
			paymentGatewayDetails = paymentGatewayHelper.savePaymentGatewayDetails(paymentGatewayDetails);

			paymentGatewayRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentGatewayRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return paymentGatewayRequest;
		} else {
			paymentGatewayRequest.setRespCode(Constant.ALREADY_EXISTS);
			paymentGatewayRequest.setRespMesg("Already exist");
			return paymentGatewayRequest;
		}
	}

	public List<PaymentGatewayDetails> getPaymentGatewayDetailsList(
			Request<PaymentRequestObject> paymentModeRequestObject) {
		PaymentRequestObject paymentModeRequest = paymentModeRequestObject.getPayload();

		List<PaymentGatewayDetails> paymentGatewayDetails = new ArrayList<>();
		paymentGatewayDetails = paymentGatewayHelper.getPaymentGatewayDetailsList(paymentModeRequest);
		return paymentGatewayDetails;
	}
	

//	public JSONObject updatePgResponseDetails(String decodedString) {
//		
//		PaymentRequestObject paymentGatewayRequest = new PaymentRequestObject();
//
//		JSONObject jsonObject = new JSONObject(decodedString);
//		JSONObject data = jsonObject.getJSONObject("data");
//		
////		String code = jsonObject.getString("code");
////		boolean success = jsonObject.getBoolean("success");
////		String merchantId = data.getString("merchantId");
////		String merchantTransactionId = data.getString("merchantTransactionId");		
////		String transactionId = data.getString("transactionId");
////		String state = data.getString("state");
////		String responseCode = data.getString("responseCode");
//		
//		paymentGatewayRequest.setResponseCode(data.getString("responseCode"));
//		paymentGatewayRequest.setStatus(data.getString("state"));
//		paymentGatewayRequest.setTransactionId(data.getString("transactionId"));
//		
//		paymentGatewayRequest.setMerchantId(data.getString("merchantId"));
//		
//		PaymentGatewayResponseDetails paymentResponseDetails = phonePePgHelper.getPaymentGatewayResponseDetailsBySuperadminId(data.getString("merchantId"), data.getString("merchantTransactionId"));
//		if(paymentResponseDetails != null) {
//			
//			phonePePgHelper.getUpdatedPaymentDetailsByReqObj(paymentResponseDetails,paymentGatewayRequest);
//			phonePePgHelper.UpdatePaymentGatewayResponseDetails(paymentResponseDetails);
//			
//		}
//
//
//		return null;
//	}

}
