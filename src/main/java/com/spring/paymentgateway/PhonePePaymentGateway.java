package com.spring.paymentgateway;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.PhonePePgHelper;
import com.spring.object.request.DonationRequestObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Component
public class PhonePePaymentGateway {

	
	@Autowired
	private PhonePePgHelper phonePePgHelper;


	public DonationRequestObject getPhonePePaymentLink(DonationRequestObject donationRequest,
			DonationDetails donationDetails, PaymentGatewayDetails paymentGatewayDetails) throws BizException, Exception {

		donationRequest.setMerchantId(paymentGatewayDetails.getMerchantId());
		donationRequest.setSaltIndex(paymentGatewayDetails.getSaltIndex());
		donationRequest.setSaltKey(paymentGatewayDetails.getSaltKey());

		// Save Payment Details
		PaymentGatewayResponseDetails paymentGatewayResponseDetails = phonePePgHelper.getPaymentDetailsByReqObj(donationDetails, donationRequest);
		paymentGatewayResponseDetails = phonePePgHelper.savePaymentDetails(paymentGatewayResponseDetails);

		// Call Payment Gateway API
		String param = this.getPaymetGatewayParam(donationDetails, paymentGatewayDetails);

		String jsonResponse = this.getPaymentPageRequest(param, paymentGatewayDetails);

//		String jsonResponse = "{\"success\":true,\"code\":\"PAYMENT_INITIATED\",\"message\":\"Payment initiated\",\"data\":{\"merchantId\":\"M22XLI1BBSR4N\",\"merchantTransactionId\":\"CI/CEF/022024/4697\",\"instrumentResponse\":{\"type\":\"PAY_PAGE\",\"redirectInfo\":{\"url\":\"https://mercury-t2.phonepe.com/transact/pg?token=NGMzYzdhZDM5ODkwMWNiM2U0OTc4NmY2MGVhMDU2N2Y5NzM0M2I1MTJkYmZiNDc3MDVhNDYwNjdjNzY3YTc5YjFlNGNkOTkyZTlmYTZhZmRhZjZjYjczOThjYTQ1ODM1OjQ2NWNlYmE3YjIxZDJjNDM3NmMzNWYxMTMxYjdjNDdm\",\"method\":\"GET\"}}}}";

		JSONObject jsonObject = new JSONObject(jsonResponse);
		String code = jsonObject.getString("code");
		boolean success = jsonObject.getBoolean("success");
		if (success) {

			JSONObject data = jsonObject.getJSONObject("data");
			JSONObject instrumentResponse = data.getJSONObject("instrumentResponse");
			JSONObject redirectInfo = instrumentResponse.getJSONObject("redirectInfo");
			String paymentUrl = redirectInfo.getString("url");

			donationRequest.setPaymentGatewayPageRedirectUrl(paymentUrl);
			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Register & Payment Link Send");
			return donationRequest;
		} else {
			// payment Faild
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg("Donation Saved but paymentgateway Faild " + code);
			return donationRequest;
		}
	}

	public String getPaymetGatewayParam(DonationDetails donationDetails, PaymentGatewayDetails paymentGatewayDetails)
			throws Exception {
		JSONObject parameters = new JSONObject();
		JSONObject paymentInstrument = new JSONObject();

		paymentInstrument.put("type", "PAY_PAGE");

		parameters.put("merchantId", paymentGatewayDetails.getMerchantId());
		parameters.put("merchantTransactionId",donationDetails.getReceiptNumber());
		parameters.put("merchantUserId", donationDetails.getCreatedBy());
		parameters.put("amount", donationDetails.getAmount().longValue());
		parameters.put("redirectUrl", "https://datfuslab.com/successfull");
		parameters.put("redirectMode", "REDIRECT");
		parameters.put("callbackUrl", "https://datafusionlab.co.in:8080/mycrm/phonePePgResponse");
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

	public String getPaymentPageRequest(String param, PaymentGatewayDetails paymentGatewayDetails)
			throws NoSuchAlgorithmException, IOException {
		String result = "";
		String encodedString = Base64.getEncoder().encodeToString(param.getBytes());
		String xVeryfy = getSha256(encodedString, paymentGatewayDetails.getSaltKey());

		OkHttpClient client = new OkHttpClient();

		JSONObject requestedParam = new JSONObject();
		requestedParam.put("request", encodedString);

		// Build the request body with JSON content
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestedParam.toString());

		// Build the request
		Request request = new Request.Builder()
				.url("https://api.phonepe.com/apis/hermes/pg/v1/pay").post(body)
				.addHeader("accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.addHeader("X-VERIFY", xVeryfy + "###" + paymentGatewayDetails.getSaltIndex())
				.build();
		

		// Execute the request
		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				result = response.body().string();
				return result;
			} else {
				System.out.println("Unexpected response code: " + response);
				result = response.body().string();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            if (response != null) {
                response.body().close();
            }
        }
		return result;
	}

}
