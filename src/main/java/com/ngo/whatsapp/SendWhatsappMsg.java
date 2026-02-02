package com.ngo.whatsapp;

import okhttp3.*;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.ngo.entities.DonationDetails;

@Component
public class SendWhatsappMsg {
	
	public String buildTemplateJsonBody(DonationDetails donationDetails) {

	    JSONObject json = new JSONObject();

	    json.put("from_phone_number_id", "865790746618248");
	    json.put("phone_number", "91"+donationDetails.getMobileNumber());
	    json.put("template_name", "donation_receipt1");
	    json.put("template_language", "en_US");
	    json.put("template_media_type", "image");
	    json.put("url", "https://login.mart2meta.com/MART2META_logo.png");
	    json.put("parameters", "{"+donationDetails.getDonorName()+"},{"+donationDetails.getAmount()+"},{"+donationDetails.getReceiptNumber()+"}");

	    return json.toString();
	}

	public void sendFixedWhatsAppMesageMessage(DonationDetails donationDetails) throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");

		RequestBody body = RequestBody.create(mediaType, buildTemplateJsonBody(donationDetails));

		Request request = new Request.Builder()
				.url("https://login.mart2meta.com/api/007012a1-a35e-4adf-9e7e-a3e807782a48/contact/send-template")
				.post(body)
				.addHeader("Authorization", "Bearer 6u1fEqhENLICM2yxncGUdv94BcVpRAfL0OjUp5fBM9sUfQcOyLTdNv4fJvGBOYCV")
				.addHeader("Content-Type", "application/json")
				.addHeader("Cookie", "PHPSESSID=58d22578124db1cc9a112104bfd0ad80").build();

		Response response = client.newCall(request).execute();

		System.out.println("Response Code : " + response.code());
		System.out.println("Response Body : " + response.body().string());
	}

	
}
		









