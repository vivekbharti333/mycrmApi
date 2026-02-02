package com.ngo.services;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngo.entities.DonationDetails;
import com.ngo.entities.PaymentGatewayResponseDetails;
import com.ngo.helper.DonationHelper;
import com.ngo.helper.PaymentGatewayResponseHelper;
import com.ngo.helper.PhonePePgHelper;
import com.ngo.object.request.PaymentRequestObject;

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
