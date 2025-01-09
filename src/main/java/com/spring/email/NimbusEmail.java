package com.spring.email;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.spring.entities.DonationDetails;
import com.spring.entities.EmailServiceDetails;

@Component
public class NimbusEmail {

	public String getParameter(DonationDetails donationDetails, EmailServiceDetails emailServiceDetails) {
		JSONObject paramJson = new JSONObject();
//		paramJson.put("DONOR", "Vivek Bharti");
//		paramJson.put("COMPANY_NAME", "Datfuslab Technologies Pvt. Ltd.");
//		paramJson.put("AMOUNT", "&#x20B9;" + 500);
//		paramJson.put("PROGRAM", "Child Education");
//        paramJson.put("RECIEPT_URL", "https://mydonation.co.in/#/receipt?receiptNo=HJUY79jhgju76");
//		paramJson.put("EMAIL", "info@datfuslab.com");
		
		paramJson.put("DONOR", donationDetails.getDonorName());
		paramJson.put("COMPANY_NAME", emailServiceDetails.getRegards());
		paramJson.put("AMOUNT", "&#x20B9;" + donationDetails.getAmount());
		paramJson.put("PROGRAM", donationDetails.getProgramName());
//        paramJson.put("RECIEPT_URL", "https://mydonation.co.in/#/receipt?receiptNo="+donationDetails.getReceiptNumber());
        paramJson.put("RECIEPT_URL", "https://mydonation.co.in/#/thanku?receiptNo="+donationDetails.getReceiptNumber());
		paramJson.put("EMAIL", emailServiceDetails.getWebsite());

//		System.out.println("Param0 : " + paramJson.toString());
//		System.out.println("Param : " + paramJson.toString(4)); 
		return paramJson.toString();
	}

	public void sendNimbusEmail(DonationDetails donationDetails, EmailServiceDetails emailServiceDetails) {
		System.out.println("Email");
//	public void sendNimbusEmail(DonationDetails donationDetails) {
		try {

//			URL url = new URL("http://trans.nimbusitsolutions.com/api/data/");
			URL url = new URL(emailServiceDetails.getHost());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Prepare POST data
			Map<String, String> postData = new HashMap<>();
//			postData.put("APP-ID", "ZpbQ9krjJl2DenhUsIdGTwVFLtOKq0mA");
//			postData.put("SECRET-KEY", "5wW01PchK23b6UmCDBpadOTzIRsLXyot");
//			postData.put("TEMPLATE_UID", "671f533865e38");
//			postData.put("SENDER_UID", "671a3e244abea");
//			postData.put("TO", "vivekbharti333@gmail.com");
			postData.put("APP-ID", emailServiceDetails.getEmailUserid());
			postData.put("SECRET-KEY", emailServiceDetails.getEmailPassword());
			postData.put("TEMPLATE_UID", emailServiceDetails.getEmailBody());
			postData.put("SENDER_UID", emailServiceDetails.getEmailFrom());
			postData.put("TO", donationDetails.getEmailId());
			postData.put("PARAMS", this.getParameter(donationDetails, emailServiceDetails));
//            postData.put("PARAMS", "{\"DONOR\":\"Vivek\",\"COMPANY_NAME\":\"Datfuslab Technologies Pvt. Ltd.\",\"AMOUNT\":\"&#x20B9;250\",\"PROGRAM\":\"Child Education\",\"RECIEPT\":\"HJUY79jhgju\76\",\"EMAIL\":\"info@datfuslab.com\",\"COMPANY_NAME\":\"Datfuslab Technologies Pvt. Ltd.\",\"EMAIL\":\"info@datfuslab.com\"}");
//			postData.put("FILE[0]", "http://localhost/mycrm/donationinvoice12/6202cx41893");
			postData.put("FILE[0]", "");

			// Encode the PDF content as Base64
//            String pdfBase64 = Base64.getEncoder().encodeToString(pdfContent.toByteArray());
//            postData.put("DOCUMENT", pdfBase64);
//            
//            System.out.println("Base64 : "+pdfBase64);

			// Convert postData map to URL encoded string
			StringBuilder postDataStringBuilder = new StringBuilder();
			for (Map.Entry<String, String> entry : postData.entrySet()) {
				if (postDataStringBuilder.length() != 0)
					postDataStringBuilder.append('&');
				postDataStringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
				postDataStringBuilder.append('=');
				postDataStringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			}

			// Write POST data to the connection
			try (OutputStream os = connection.getOutputStream();
					OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8")) {
				writer.write(postDataStringBuilder.toString());
			}

			// Get the response
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
//				System.out.println("Request successful");

				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					String inputLine;
					StringBuilder response = new StringBuilder();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}

					// Parse the response as JSON
					JSONObject jsonObject = new JSONObject(response.toString());
//					System.out.println("status: " + jsonObject.getInt("status"));
//					System.out.println("message: " + jsonObject.getString("message"));
				}
			} else {
				System.out.println("Request failed with response code: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
