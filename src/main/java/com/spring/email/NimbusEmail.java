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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.common.PdfInvoice;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.EmailServiceDetails;

@Component
public class NimbusEmail {
	
	@Autowired
	private PdfInvoice pdfInvoice;

	public String getParameter(DonationDetails donationDetails, EmailServiceDetails emailServiceDetails) {
		JSONObject paramJson = new JSONObject();
		
		paramJson.put("DONOR", donationDetails.getDonorName());
		paramJson.put("COMPANY_NAME", emailServiceDetails.getRegards());
		paramJson.put("AMOUNT", "&#x20B9;" + donationDetails.getAmount());
		paramJson.put("PROGRAM", donationDetails.getProgramName());
//        paramJson.put("RECIEPT_URL", "https://mydonation.co.in/#/receipt?receiptNo="+donationDetails.getReceiptNumber());
        paramJson.put("RECIEPT_URL", "https://mydonation.co.in/#/receipt?receiptNo="+donationDetails.getReceiptNumber());
		paramJson.put("EMAIL", emailServiceDetails.getWebsite());

		return paramJson.toString();
	}

	public void sendNimbusEmail(DonationDetails donationDetails, EmailServiceDetails emailServiceDetails) {

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
			postData.put("APP-ID", emailServiceDetails.getEmailUserid());
			postData.put("SECRET-KEY", emailServiceDetails.getEmailPassword());
			postData.put("TEMPLATE_UID", emailServiceDetails.getEmailBody());
			postData.put("SENDER_UID", emailServiceDetails.getEmailFrom());
			postData.put("TO", donationDetails.getEmailId());
			postData.put("PARAMS", this.getParameter(donationDetails, emailServiceDetails));
//            postData.put("PARAMS", "{\"DONOR\":\"Vivek\",\"COMPANY_NAME\":\"Datfuslab Technologies Pvt. Ltd.\",\"AMOUNT\":\"&#x20B9;250\",\"PROGRAM\":\"Child Education\",\"RECIEPT\":\"HJUY79jhgju\76\",\"EMAIL\":\"info@datfuslab.com\",\"COMPANY_NAME\":\"Datfuslab Technologies Pvt. Ltd.\",\"EMAIL\":\"info@datfuslab.com\"}");
			postData.put("FILE[0]", "https://morth.nic.in/sites/default/files/dd12-13_0.pdf");
//			postData.put("FILE[0]", Constant.baseURL+"getPdfreceipt/" + donationDetails.getReceiptNumber() +".pdf");

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
					
//					if(response != null) {
//						pdfInvoice.deleteInvoiceFile(donationDetails);
//					}

					// Parse the response as JSON
					JSONObject jsonObject = new JSONObject(response.toString());
					
					System.out.println("response : "+response);
					System.out.println("status: " + jsonObject.getInt("status"));
					System.out.println("message: " + jsonObject.getString("message"));
				}
			} else {
				System.out.println("Request failed with response code: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
