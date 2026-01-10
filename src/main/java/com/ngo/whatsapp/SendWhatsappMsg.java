package com.ngo.whatsapp;

import okhttp3.*;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.ngo.entities.DonationDetails;
import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.SecureRandom;

@Component
public class SendWhatsappMsg {
	
	 public static OkHttpClient getUnsafeOkHttpClient() {
	        try {
	            TrustManager[] trustAllCerts = new TrustManager[]{
	                new X509TrustManager() {
	                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
	                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
	                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                        return new java.security.cert.X509Certificate[]{};
	                    }
	                }
	            };

	            SSLContext sslContext = SSLContext.getInstance("SSL");
	            sslContext.init(null, trustAllCerts, new SecureRandom());

	            return new OkHttpClient.Builder()
	                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
	                    .hostnameVerifier((hostname, session) -> true)
	                    .build();

	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }


	
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

//	public void sendFixedWhatsAppMesageMessage(DonationDetails donationDetails) throws IOException {
//
//		OkHttpClient client = new OkHttpClient().newBuilder().build();
//		MediaType mediaType = MediaType.parse("application/json");
//
//		RequestBody body = RequestBody.create(mediaType, buildTemplateJsonBody(donationDetails));
//
//		Request request = new Request.Builder()
//				.url("https://login.mart2meta.com/api/007012a1-a35e-4adf-9e7e-a3e807782a48/contact/send-template")
//				.post(body)
//				.addHeader("Authorization", "Bearer 6u1fEqhENLICM2yxncGUdv94BcVpRAfL0OjUp5fBM9sUfQcOyLTdNv4fJvGBOYCV")
//				.addHeader("Content-Type", "application/json")
////				.addHeader("Cookie", "PHPSESSID=58d22578124db1cc9a112104bfd0ad80")
//				.build();
//
//		Response response = client.newCall(request).execute();
//
//		System.out.println("Response Code : " + response.code());
//		System.out.println("Response Body : " + response.body().string());
//	}

	
//	  public static void sendFixedWhatsAppMesageMessage(DonationDetails donationDetails) throws Exception {
//
//	        OkHttpClient client = getUnsafeOkHttpClient();
//	        MediaType mediaType = MediaType.parse("application/json");
//	        RequestBody body = RequestBody.create(mediaType, this.buildTemplateJsonBody(donationDetails));
//
//	        Request request = new Request.Builder()
//	                .url("https://login.mart2meta.com/api/007012a1-a35e-4adf-9e7e-a3e807782a48/contact/send-template")
//	                .post(body)
//	                .addHeader("Authorization", "Bearer 6u1fEqhENLICM2yxncGUdv94BcVpRAfL0OjUp5fBM9sUfQcOyLTdNv4fJvGBOYCV")
//	                .addHeader("Content-Type", "application/json")
//	                .build();
//
//	        Response response = client.newCall(request).execute();
//
//	        System.out.println("Response Code : " + response.code());
//	        System.out.println("Response Body : " + response.body().string());
//	    }
	
	
	public void sendFixedWhatsAppMesageMessage(DonationDetails donationDetails) throws IOException {

	    OkHttpClient client = getUnsafeOkHttpClient();

	    MediaType mediaType = MediaType.parse("application/json");

	    String json = buildTemplateJsonBody(donationDetails);

	    RequestBody body = RequestBody.create(json, mediaType);

	    Request request = new Request.Builder()
	            .url("https://login.mart2meta.com/api/007012a1-a35e-4adf-9e7e-a3e807782a48/contact/send-template")
	            .post(body)
	            .addHeader("Authorization", "Bearer 6u1fEqhENLICM2yxncGUdv94BcVpRAfL0OjUp5fBM9sUfQcOyLTdNv4fJvGBOYCV")
	            .addHeader("Content-Type", "application/json")
	            .build();

	    Response response = client.newCall(request).execute();

	    System.out.println("Response Code : " + response.code());
	    System.out.println("Response Body : " + response.body().string());
	}
	
}
		









