package com.spring.paymentgateway;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;

@Component
public class RozarpayPaymentGateway {


	public String getRozarpayPaymentLink(DonationRequestObject vehicleRequest )
			throws BizException, Exception {
		
		
		RazorpayClient razorpay = new RazorpayClient("rzp_test_KiREBJNBMR41TP", "It78jFUUZQZl7f3d6I6iUAI2");
		JSONObject paymentLinkRequest = new JSONObject();
		paymentLinkRequest.put("amount", vehicleRequest.getAmount());
		paymentLinkRequest.put("currency","INR");
		paymentLinkRequest.put("accept_partial",false);
		paymentLinkRequest.put("expire_by",1750346930);
		paymentLinkRequest.put("reference_id", vehicleRequest.getInvoiceNumber());
		JSONObject customer = new JSONObject();
		customer.put("name", vehicleRequest.getDonorName());
		customer.put("contact", vehicleRequest.getMobileNumber());
		customer.put("email", vehicleRequest.getEmailId());
		paymentLinkRequest.put("customer",customer);
		JSONObject notify = new JSONObject();
		notify.put("sms",true);
		notify.put("email",true);
		paymentLinkRequest.put("notify",notify);
		paymentLinkRequest.put("reminder_enable",true);
		paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
		paymentLinkRequest.put("callback_method","get");
		              
		PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
		System.out.println("Payment : "+payment);
		
		
		JSONObject jsonObject = new JSONObject(payment.toString());

        // Get and print the short_url
        String shortUrl = jsonObject.getString("short_url");

		return shortUrl;
	}
}
