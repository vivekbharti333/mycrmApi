package com.spring.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.PaymentGatewayHelper;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;

@Service
public class PaymentGatewayService {

	@Autowired
	private PaymentGatewayHelper paymentGatewayHelper;

//	@Autowired
//	private PhonePePgHelper phonePePgHelper; 

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public PaymentRequestObject addUpdatePaymentGatewayDetails(Request<PaymentRequestObject> paymentRequestObject)
			throws BizException, Exception {
		PaymentRequestObject paymentGatewayRequest = paymentRequestObject.getPayload();
		paymentGatewayHelper.validatePaymentModeRequest(paymentGatewayRequest);

		PaymentGatewayDetails existsPaymentGateway = paymentGatewayHelper.getPaymentGatewayDetailsBySuperadminIdNpg(
				paymentGatewayRequest.getSuperadminId(), paymentGatewayRequest.getPgProvider());
		if (existsPaymentGateway == null) {
			PaymentGatewayDetails paymentGatewayDetails = paymentGatewayHelper
					.getPaymentGatewayByReqObj(paymentGatewayRequest);
			paymentGatewayDetails = paymentGatewayHelper.savePaymentGatewayDetails(paymentGatewayDetails);

			paymentGatewayRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentGatewayRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return paymentGatewayRequest;
		} else {
			existsPaymentGateway = paymentGatewayHelper.getUpdatedPaymentGatewayByReqObj(paymentGatewayRequest,
					existsPaymentGateway);
			existsPaymentGateway = paymentGatewayHelper.updatePaymentGatewayDetails(existsPaymentGateway);

			paymentGatewayRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentGatewayRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return paymentGatewayRequest;
		}
	}

	public List<PaymentGatewayDetails> getPaymentGatewayDetailsList(
			Request<PaymentRequestObject> paymentRequestObject) {
		PaymentRequestObject paymentRequest = paymentRequestObject.getPayload();

		List<PaymentGatewayDetails> paymentGatewayDetails = new ArrayList<>();
		paymentGatewayDetails = paymentGatewayHelper.getPaymentGatewayDetailsList(paymentRequest, paymentRequest.getRequestedFor());
		return paymentGatewayDetails;
	}

	@Transactional
	public PaymentRequestObject changePaymentGatewayStatus(Request<PaymentRequestObject> paymentRequestObject)
			throws BizException, Exception {
		PaymentRequestObject paymentGatewayRequest = paymentRequestObject.getPayload();

		List<PaymentGatewayDetails> paymentGatewayDetails = paymentGatewayHelper.getPaymentGatewayDetailsList(paymentGatewayRequest, "SUPERADMIN");
		for (PaymentGatewayDetails pgDetails : paymentGatewayDetails) {
			pgDetails.setStatus(Status.INACTIVE.name());
			paymentGatewayHelper.updatePaymentGatewayDetails(pgDetails);
		}

		PaymentGatewayDetails paymentGateway = paymentGatewayHelper.getPaymentGatewayDetailsBySuperadminIdNpg(paymentGatewayRequest.getSuperadminId(), paymentGatewayRequest.getPgProvider());

		paymentGateway.setStatus(paymentGatewayRequest.getStatus());
		paymentGatewayHelper.updatePaymentGatewayDetails(paymentGateway);

		paymentGatewayRequest.setRespCode(Constant.SUCCESS_CODE);
		paymentGatewayRequest.setRespMesg(Constant.UPDATED_SUCCESS);
		return paymentGatewayRequest;
	}

}
