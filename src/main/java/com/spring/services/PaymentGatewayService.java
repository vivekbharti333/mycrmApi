package com.spring.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.OptionHandler;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.entities.PaymentModeBySuperadmin;
import com.spring.entities.PaymentModeMaster;
import com.spring.entities.UserDetails;
import com.spring.enums.RequestFor;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.helper.PaymentGatewayHelper;
import com.spring.helper.PaymentModeHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;

@Service
public class PaymentGatewayService {

	@Autowired
	private PaymentGatewayHelper paymentGatewayHelper;

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
	

	public JSONObject updatePgResponseDetails(String decodedString) {
		
		PaymentRequestObject paymentGatewayRequest = new PaymentRequestObject();

		JSONObject jsonObject = new JSONObject(decodedString);
		String code = jsonObject.getString("code");
		boolean success = jsonObject.getBoolean("success");
		
		JSONObject data = jsonObject.getJSONObject("data");
		String merchantId = data.getString("merchantId");
		String merchantTransactionId = data.getString("merchantTransactionId");
		String transactionId = data.getString("transactionId");
		String state = data.getString("state");
		String responseCode = data.getString("responseCode");
		
		paymentGatewayRequest.setResponseCode(data.getString("responseCode"));
		paymentGatewayRequest.setStatus(data.getString("state"));
		paymentGatewayRequest.setTransactionId(data.getString("transactionId"));
		
		System.out.println("code : "+code);
		System.out.println("success : "+success);
		System.out.println("merchantId : "+merchantId);
		System.out.println("merchantTransactionId : "+merchantTransactionId);
		System.out.println("transactionId : "+transactionId);
		System.out.println("state : "+state);
		System.out.println("responseCode : "+responseCode);
		System.out.println();
		
		PaymentGatewayResponseDetails paymentResponseDetails = paymentGatewayHelper.getPaymentGatewayResponseDetailsBySuperadminId(merchantId, merchantTransactionId);
		if(paymentResponseDetails != null) {
			
			paymentGatewayHelper.getUpdatedPaymentDetailsByReqObj(paymentResponseDetails,paymentGatewayRequest);
			paymentGatewayHelper.UpdatePaymentGatewayResponseDetails(paymentResponseDetails);
			
		}


		return null;
	}

}
