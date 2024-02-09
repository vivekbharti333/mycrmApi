package com.spring.paymentgateway;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.dao.SmsTemplateDetailsDao;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Component
public class PhonePePaymentGateway {

	@Autowired
	private SmsTemplateDetailsDao smsDetailsDao;

//	public String getPaymetGatewayParam() {
//		{
//			  parameters.put("merchantId": "M22XLI1BBSR4N")
//			  "merchantTransactionId": "MT7850590068188104",
//			  "merchantUserId": "MUID123",
//			  "amount": 100,
//			  "redirectUrl": "https://webhook.site/redirect-url",
//			  "redirectMode": "REDIRECT",
//			  "callbackUrl": "https://webhook.site/callback-url",
//			  "mobileNumber": "8800689752",
//			  "paymentInstrument": {
//			    "type": "PAY_PAGE"
//			  }
//			}
//	}
	
	//String hii ="ewogICJtZXJjaGFudElkIjogIlBHVEVTVFBBWVVBVCIsCiAgIm1lcmNoYW50VHJhbnNhY3Rpb25JZCI6ICJNVDc4NTA1OTAwNjgxODgxMDQiLAogICJtZXJjaGFudFVzZXJJZCI6ICJNVUlEMTIzIiwKICAiYW1vdW50IjogMTAwMDAsCiAgInJlZGlyZWN0VXJsIjogImh0dHBzOi8vd2ViaG9vay5zaXRlL3JlZGlyZWN0LXVybCIsCiAgInJlZGlyZWN0TW9kZSI6ICJSRURJUkVDVCIsCiAgImNhbGxiYWNrVXJsIjogImh0dHBzOi8vd2ViaG9vay5zaXRlL2NhbGxiYWNrLXVybCIsCiAgIm1vYmlsZU51bWJlciI6ICI5OTk5OTk5OTk5IiwKICAicGF5bWVudEluc3RydW1lbnQiOiB7CiAgICAidHlwZSI6ICJQQVlfUEFHRSIKICB9Cn0=";
	

	public String getPaymetGatewayParam() throws Exception {
		JSONObject parameters = new JSONObject();
		JSONObject paymentInstrument = new JSONObject();

		paymentInstrument.put("type", "PAY_PAGE");

		parameters.put("merchantId", "PGTESTPAYUAT");
		parameters.put("merchantTransactionId", "MT7850590068188104");
		parameters.put("merchantUserId", "MUID123");
		parameters.put("amount", 100);
		parameters.put("redirectUrl", "https://datfuslab.com/successfull");
		parameters.put("redirectMode", "REDIRECT");
		parameters.put("callbackUrl", "https://datfuslab.com/successfull");
		parameters.put("mobileNumber", "8800689752");
		parameters.put("paymentInstrument", paymentInstrument);

		System.out.println(parameters);

		return parameters.toString();
	}

	public String getSha256(String param, String saltKey) throws NoSuchAlgorithmException {

		String originalString = param + "/pg/v1/pay" + saltKey;
		
		System.out.println("originalString : "+originalString);

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

	public void paymentPageTest(String param) throws NoSuchAlgorithmException {
		
		String encodedString = Base64.getEncoder().encodeToString(param.getBytes());
		
		String xVeryfy = getSha256(encodedString, "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399");  
//		String xVeryfy = getSha256(hii, "c70a2e18-ccd9-4570-b800-b431ad5ff0c5");  

		System.out.println("Encoded String: " + encodedString);
		OkHttpClient client = new OkHttpClient();

		// Define the JSON payload
		// String jsonPayload = "{\"request\":\"ewogICJtZXJjaGFudElkIjogIlBHVEVTVFBBWVVBVCIsCiAgIm1lcmNoYW50VHJhbnNhY3Rpb25JZCI6ICJNVDc4NTA1OTAwNjgxODgxMDQiLAogICJtZXJjaGFudFVzZXJJZCI6ICJNVUlEMTIzIiwKICAiYW1vdW50IjogMTAwMDAsCiAgInJlZGlyZWN0VXJsIjogImh0dHBzOi8vd2ViaG9vay5zaXRlL3JlZGlyZWN0LXVybCIsCiAgInJlZGlyZWN0TW9kZSI6ICJSRURJUkVDVCIsCiAgImNhbGxiYWNrVXJsIjogImh0dHBzOi8vd2ViaG9vay5zaXRlL2NhbGxiYWNrLXVybCIsCiAgIm1vYmlsZU51bWJlciI6ICI5OTk5OTk5OTk5IiwKICAicGF5bWVudEluc3RydW1lbnQiOiB7CiAgICAidHlwZSI6ICJQQVlfUEFHRSIKICB9Cn0=\"}";
		// String jsonPayload = "{\"request\":\"ewogICJhbW91bnQiOiAxMDAsCiAgInJlZGlyZWN0VXJsIjogImh0dHBzOi8vZGF0ZnVzbGFiLmNvbS9zdWNjZXNzZnVsbCIsCiAgIm1lcmNoYW50SWQiOiAiTTIyWExJMUJCU1I0TiIsCiAgInJlZGlyZWN0TW9kZSI6ICJSRURJUkVDVCIsCiAgIm1vYmlsZU51bWJlciI6ICI4ODAwNjg5NzUyIiwKICAibWVyY2hhbnRUcmFuc2FjdGlvbklkIjogIk1UNzg1MDU5MDA2ODE4ODEwNCIsCiAgImNhbGxiYWNrVXJsIjogImh0dHBzOi8vZGF0ZnVzbGFiLmNvbS9zdWNjZXNzZnVsbCIsCiAgIm1lcmNoYW50VXNlcklkIjogIk1VSUQxMjMiLAogICJwYXltZW50SW5zdHJ1bWVudCI6IHsKICAgICJwYXltZW50SW5zdHJ1bWVudCI6ICJQQVlfUEFHRSIKICB9Cn0=\"}";
		
		JSONObject requestedParam = new JSONObject();
		requestedParam.put("request", encodedString);
		
		System.out.println("requestedParam: "+requestedParam);
		//String jsonPayload = "{\"request\":\"}";

		// Build the request body with JSON content
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestedParam.toString());
//		RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonPayload);

		// Build the request
		Request request = new Request.Builder().url("https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay")
				.post(body).addHeader("accept", "application/json")
				.addHeader("Content-Type", "application/json")
//	                     .addHeader("X-VERIFY", "d7a8e4458caa6fcd781166bbdc85fec76740c18cb9baa9a4c48cf2387d554180###1")
				.addHeader("X-VERIFY", xVeryfy+"###1").build();
		
		System.out.println("xVeryfy : "+xVeryfy);

		// Execute the request
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				System.out.println("Response: " + response.body().string());
			} else {
				System.out.println("Unexpected response code: " + response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
