package com.spring.paymentgateway;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.dao.SmsTemplateDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.EmailServiceDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.SmsTemplateDetails;
import com.spring.enums.Status;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.services.InvoiceService;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
	
	public String getPaymetGatewayParam() throws Exception {
		JSONObject parameters = new JSONObject();
		JSONObject paymentInstrument = new JSONObject();

		paymentInstrument.put("paymentInstrument", "PAY_PAGE");

		parameters.put("merchantId", "M22XLI1BBSR4N");
		parameters.put("merchantTransactionId", "MT7850590068188104");
		parameters.put("merchantUserId", "MUID123");
		parameters.put("amount", 100);
		parameters.put("redirectUrl", "https://webhook.site/redirect-url");
		parameters.put("redirectMode", "REDIRECT");
		parameters.put("callbackUrl", "https://webhook.site/callback-url");
		parameters.put("mobileNumber", "8800689752");
		parameters.put("paymentInstrument", paymentInstrument);
		
		System.out.println("Paraeter : "+parameters);

		return parameters.toString();
	}


	        public void paymentPageTest() {
	        	 OkHttpClient client = new OkHttpClient();

	             // Define the JSON payload
	             String jsonPayload = "{\"request\":\"eyJhbW91bnQiOjEwMCwicmVkaXJlY3RVcmwiOiJodHRwczovL3dlYmhvb2suc2l0ZS9yZWRpcmVjdC11cmwiLCJtZXJjaGFudElkIjoiTTIyWExJMUJCU1I0Tg0KICIsInJlZGlyZWN0TW9kZSI6IlJFRElSRUNUIiwibW9iaWxlTnVtYmVyIjoiODgwMDY4OTc1MiIsIm1lcmNoYW50VHJhbnNhY3Rpb25JZCI6Ik1UNzg1MDU5MDA2ODE4ODEwNCIsImNhbGxiYWNrVXJsIjoiaHR0cHM6Ly93ZWJob29rLnNpdGUvY2FsbGJhY2stdXJsIiwibWVyY2hhbnRVc2VySWQiOiJNVUlEMTIzIiwicGF5bWVudEluc3RydW1lbnQiOnsicGF5bWVudEluc3RydW1lbnQiOiJQQVlfUEFHRSJ9fQ==\"}";

	             // Build the request body with JSON content
	             RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonPayload);

	             // Build the request
	             Request request = new Request.Builder()
	                     .url("https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay")
	                     .post(body)
//	                     .addHeader("accept", "text/plain")
	                     .addHeader("Content-Type", "application/json")
	                     .addHeader("X-VERIFY", "8C2465490E1A2FD81B6EEA05F3C4CCCA135DEE574714583BD961D5FC1FD291DD###1")
	                     .build();

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
