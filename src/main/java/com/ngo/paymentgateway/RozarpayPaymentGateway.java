package com.ngo.paymentgateway;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.common.exceptions.BizException;
import com.ngo.object.request.DonationRequestObject;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;

@Component
public class RozarpayPaymentGateway {


	public String getRozarpayPaymentLink(DonationRequestObject donationRequest )
			throws BizException, Exception {
		
		
		RazorpayClient razorpay = new RazorpayClient("rzp_test_KiREBJNBMR41TP", "It78jFUUZQZl7f3d6I6iUAI2");
		JSONObject paymentLinkRequest = new JSONObject();
		paymentLinkRequest.put("amount", donationRequest.getAmount());
		paymentLinkRequest.put("currency","INR");
		paymentLinkRequest.put("accept_partial",false);
		paymentLinkRequest.put("expire_by",1750346930);
		paymentLinkRequest.put("reference_id", donationRequest.getInvoiceNumber());
		JSONObject customer = new JSONObject();
		customer.put("name", donationRequest.getDonorName());
		customer.put("contact", donationRequest.getMobileNumber());
		customer.put("email", donationRequest.getEmailId());
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
