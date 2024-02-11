package com.spring.paymentgateway;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.spring.entities.DonationDetails;
import com.spring.entities.PaymentGatewayDetails;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Component
public class PhonePePaymentGateway {


	public String getPaymetGatewayParam(DonationDetails donationDetails, PaymentGatewayDetails paymentGatewayDetails) throws Exception {
		JSONObject parameters = new JSONObject();
		JSONObject paymentInstrument = new JSONObject();

		paymentInstrument.put("type", "PAY_PAGE");

//		parameters.put("merchantId", "M22XLI1BBSR4N");
		parameters.put("merchantId", paymentGatewayDetails.getMerchantId());
//		parameters.put("merchantTransactionId", "MT7850590068188104");
		parameters.put("merchantTransactionId", donationDetails.getInvoiceNumber());
//		parameters.put("merchantUserId", "MUID123");
		parameters.put("merchantUserId", donationDetails.getCreatedBy());
		parameters.put("amount", donationDetails.getAmount());
		parameters.put("redirectUrl", "https://datfuslab.com/successfull");
		parameters.put("redirectMode", "REDIRECT");
		parameters.put("callbackUrl", "https://datfuslab.com/successfull");
		parameters.put("mobileNumber", donationDetails.getMobileNumber());
		parameters.put("paymentInstrument", paymentInstrument);

		return parameters.toString();
	}

	public String getSha256(String base64param, String saltKey) throws NoSuchAlgorithmException {

		String originalString = base64param + "/pg/v1/pay" + saltKey;
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public String getPaymentPageResponse(String param, PaymentGatewayDetails paymentGatewayDetails) throws NoSuchAlgorithmException {
		String result = ""; 
		String encodedString = Base64.getEncoder().encodeToString(param.getBytes());
		
//		String xVeryfy = getSha256(encodedString, "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399");  
//		String xVeryfy = getSha256(encodedString, "c70a2e18-ccd9-4570-b800-b431ad5ff0c5");  
		String xVeryfy = getSha256(encodedString, paymentGatewayDetails.getSaltKey());  

		System.out.println("Encoded String: " + encodedString);
		OkHttpClient client = new OkHttpClient();

		JSONObject requestedParam = new JSONObject();
		requestedParam.put("request", encodedString);

		// Build the request body with JSON content
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestedParam.toString());

		// Build the request
		Request request = new Request.Builder().url("https://api.phonepe.com/apis/hermes/pg/v1/pay")
				.post(body).addHeader("accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.addHeader("X-VERIFY", xVeryfy+"###"+paymentGatewayDetails.getSaltIndex()).build();
		

		// Execute the request
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				result = response.body().string();
				return result;
//				System.out.println("Response: " + response.body().string());
			} else {
				System.out.println("Unexpected response code: " + response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
