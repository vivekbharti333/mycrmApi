package com.ngo.services;

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

import com.common.constant.Constant;
import com.common.entities.UserDetails;
import com.common.enums.RequestFor;
import com.common.enums.RoleType;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.common.helper.UserHelper;
import com.common.jwt.JwtTokenUtil;
import com.ngo.entities.DonationDetails;
import com.ngo.entities.InvoiceHeaderDetails;
import com.ngo.entities.PaymentGatewayDetails;
import com.ngo.entities.PaymentGatewayResponseDetails;
import com.ngo.entities.PaymentModeBySuperadmin;
import com.ngo.entities.PaymentModeMaster;
import com.ngo.helper.DonationHelper;
import com.ngo.helper.InvoiceHelper;
import com.ngo.helper.PaymentGatewayHelper;
import com.ngo.helper.PaymentGatewayResponseHelper;
import com.ngo.helper.PaymentModeHelper;
import com.ngo.helper.PhonePePgHelper;
import com.ngo.object.request.DonationRequestObject;
import com.ngo.object.request.PaymentRequestObject;
import com.common.object.request.Request;

@Service
public class PhonePePgService {


	@Autowired
	private PhonePePgHelper phonePePgHelper;
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private PaymentGatewayResponseHelper paymentGatewayResponseHelper;

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

		PaymentGatewayResponseDetails paymentResponseDetails = paymentGatewayResponseHelper.getPaymentGatewayResponseDetailsBySuperadminId(data.getString("merchantId"),data.getString("merchantTransactionId"));
		if (paymentResponseDetails != null) {

			paymentGatewayResponseHelper.getUpdatedPaymentDetailsByReqObj(paymentResponseDetails, paymentGatewayRequest);
			paymentGatewayResponseHelper.UpdatePaymentGatewayResponseDetails(paymentResponseDetails);
			
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
