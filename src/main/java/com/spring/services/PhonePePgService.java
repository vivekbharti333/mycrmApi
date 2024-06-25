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
import com.spring.helper.PhonePePgHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;

@Service
public class PhonePePgService {


	@Autowired
	private PhonePePgHelper phonePePgHelper;
	
	@Autowired
	private DonationHelper donationHelper;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public String updatePgResponseDetails(String decodedString) {

		PaymentRequestObject paymentGatewayRequest = new PaymentRequestObject();
		
		logger.info("PhonePe PG Response : "+decodedString);

		JSONObject jsonObject = new JSONObject(decodedString);
		JSONObject data = jsonObject.getJSONObject("data");

		paymentGatewayRequest.setResponseCode(data.getString("responseCode"));
		paymentGatewayRequest.setStatus(data.getString("state"));
		paymentGatewayRequest.setTransactionId(data.getString("transactionId"));

		paymentGatewayRequest.setMerchantId(data.getString("merchantId"));

		PaymentGatewayResponseDetails paymentResponseDetails = phonePePgHelper.getPaymentGatewayResponseDetailsBySuperadminId(data.getString("merchantId"),data.getString("merchantTransactionId"));
		if (paymentResponseDetails != null) {

			phonePePgHelper.getUpdatedPaymentDetailsByReqObj(paymentResponseDetails, paymentGatewayRequest);
			phonePePgHelper.UpdatePaymentGatewayResponseDetails(paymentResponseDetails);
			
			//Status Update on donation details
			DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo(data.getString("merchantTransactionId"));
			if (donationDetails != null) {
				donationDetails.setPaymentStatus(data.getString("state"));
				donationHelper.updateDonationDetails(donationDetails);
				
				//generate invoice
			}

		}
		return data.getString("merchantTransactionId");
	}

}
