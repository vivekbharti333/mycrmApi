package com.spring.paymentgateway;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.helper.PaymentGatewayResponseHelper;
import com.spring.object.request.DonationRequestObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CashfreePaymentGateway {
	
	@Autowired
	private PaymentGatewayResponseHelper paymentGatewayResponseHelper;

	public DonationRequestObject getCashfreePaymentLink(DonationRequestObject donationRequest,
			DonationDetails donationDetails, PaymentGatewayDetails paymentGatewayDetails) throws IOException, InterruptedException {
		
		System.out.println("Enter into payment gateway 1");

        // Save Payment Details
        PaymentGatewayResponseDetails paymentGatewayResponseDetails = paymentGatewayResponseHelper.getPaymentDetailsByReqObj(donationDetails, donationRequest);
        paymentGatewayResponseDetails = paymentGatewayResponseHelper.savePaymentDetails(paymentGatewayResponseDetails);

        System.out.println("Enter into payment gateway 2");

        String param = this.getCashfreePaymentParam(donationRequest, donationDetails, paymentGatewayDetails);
        HttpResponse<String> response = this.getCashfreePaymentRequestPage(param, paymentGatewayDetails); // Now returns HttpResponse<String>

        System.out.println("Enter into payment gateway 3: " + response.statusCode());

        if (response.statusCode() == 200) {
            String responseBody = response.body();

            JSONObject json = new JSONObject(responseBody);
            String linkUrl = json.optString("link_url");
            String thankYouMsg = json.optString("thank_you_msg");

            donationRequest.setPaymentGatewayPageRedirectUrl(linkUrl);
            donationRequest.setRespCode(Constant.SUCCESS_CODE);
            donationRequest.setRespMesg(thankYouMsg);
        } else {
            donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
            donationRequest.setRespMesg("Something went wrong");
        }

        return donationRequest;
    }
	
	
	public String getCashfreePaymentParam(DonationRequestObject donationRequest, DonationDetails donationDetails,
			PaymentGatewayDetails paymentGatewayDetails) {

		JSONObject jsonBody = new JSONObject();

		jsonBody.put("link_id", donationRequest.getReceiptNumber());
		jsonBody.put("link_amount", donationDetails.getAmount());
		jsonBody.put("link_currency", "INR");
		jsonBody.put("link_purpose", "Donation");

		JSONObject customerDetails = new JSONObject();
		customerDetails.put("customer_phone", donationDetails.getMobileNumber());
		customerDetails.put("customer_email", donationDetails.getEmailId());
		customerDetails.put("customer_name", donationDetails.getDonorName());
		jsonBody.put("customer_details", customerDetails);

		JSONObject linkNotify = new JSONObject();
		linkNotify.put("send_sms", true);
		linkNotify.put("send_email", true);
		jsonBody.put("link_notify", linkNotify);

		// Added link_meta JSON object with return_url
		JSONObject linkMeta = new JSONObject();
		linkMeta.put("return_url", "https://datfuslab.in/drmapinew/cashfreeWebhook");
		jsonBody.put("link_meta", linkMeta);

		return jsonBody.toString();
	}

	    public HttpResponse<String> getCashfreePaymentRequestPage(String param, PaymentGatewayDetails paymentGatewayDetails) throws IOException, InterruptedException {
	        // Create HttpClient
	        HttpClient client = HttpClient.newHttpClient();

	        // Create request
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(paymentGatewayDetails.getUrl()))
	                .header("Content-Type", "application/json")
	                .header("x-api-version", "2025-01-01")
	                .header("x-client-id", paymentGatewayDetails.getMerchantId())
	                .header("x-client-secret", paymentGatewayDetails.getSaltKey())
	                .POST(HttpRequest.BodyPublishers.ofString(param))
	                .build();

	        // Send request
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        // Log and return
	        System.out.println("Response: " + response.body());
	        return response;
	    }
	


//	response
//	---------

//	{
//		  "cf_link_id": "6470069",
//		  "customer_details": {
//		    "customer_name": "Vivek Bharti",
//		    "country_code": "+91",
//		    "customer_phone": "8800689752",
//		    "customer_email": "support@datafusionlab.in"
//		  },
//		  "enable_invoice": false,
//		  "entity": "link",
//		  "link_amount": 200,
//		  "link_amount_paid": 0,
//		  "link_auto_reminders": false,
//		  "link_created_at": "2025-05-19T15:09:37+05:30",
//		  "link_currency": "INR",
//		  "link_expiry_time": "2025-06-18T15:09:37+05:30",
//		  "link_id": "123456",
//		  "link_meta": {
//		    "payment_methods": "",
//		    "return_url": "http://datfuslab.in",
//		    "upi_intent": "false"
//		  },
//		  "link_minimum_partial_amount": null,
//		  "link_notes": {},
//		  "link_notify": {
//		    "send_email": true,
//		    "send_sms": true
//		  },
//		  "link_partial_payments": false,
//		  "link_purpose": "Donation",
//		  "link_qrcode": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAFAAQMAAAD3XjfpAAAABlBMVEX///8AAABVwtN+AAAC5UlEQVR4nOxaMbLrIAyUh4KSI3AU3yyQm3EUjkDpgvH+WQnnfb/J/OpXYDUhZpNCI61WkuWxxx577P9aBG0DSnJA2dB2PsgbmiSe+pTALCI773P3wOnbfjoAp4i8CFgdCNSdYPCw0YfOXFheDsgTA7cjlGTuQUlAFXXPAxxANP22n/oRgO2YG6g5c8gOdxAY8O586tvX5FoNaIzL6HFHLKSUdw816ZMv1DwHcJhvkhzqfnrehZpO3+SbLQdk0PAzdQ2jUISMKxLKBtTU5wSKRzGv+LrD8qYmJg96uEXPgsCommM7ArLwMKg38cCaDJkRSPdU5syeqUjpnkEyPLS4OjAxaJhK0vUXwOmaCKmX3PKpM4sCxaGKeo3ViZn24kElPP9H5gSqJPNUYb7uJkSbCKn3ps3WBEZ0iZZT2ryRetEoaOnQUD9/NhXQwKrRRytXROuMR9moQtYGakEqYCrhiDxIYlHCYbpF+oxASUNvAO4wP727SLqSJ60NNJK9GLcwgt5X9dXomROY+9W78hfsV1Sf6bjLGGdtICWZDjy6FpzR5J4SCn2YpgSSPKLqTy0tdh+qiXV2cbI2MF338lEhDjVtxrg6DpoPaDlDcJIxD8+juupM/BelrAbU6FGiRbceX8WHaGG+a7OJgPGzG8kA78fcz5r93yplOSC7OE0u5tRlLQLapvytUuYCdu1UaYe1rO8ekM+f66WBWkiUPMRbj3+6oc3uZXhJYMwm2MXmYkq99Cx0lnxbS08EFL2z5YCyrdhwi9GkjLs2MGYZQ0C2c5Rk5UUiIuMC93cV5gGaqSId22acY/BTdD64NtA2kAyazC8Mo4QmabsKkkwJ1G3ztVj8pEoau7b467Wd9YD6AsvYstobTc4W1dDZmMwLNGfYQbfNV5n9SauVgZpc7jCRppMPHRF/f/1pCuDIGSNSbe95T+DW5QulrAW8GFdEd0g6+6Ng9/oy5KTAxx577LF/258AAAD//+aTNe1Oyd/WAAAAAElFTkSuQmCC",
//		  "link_status": "ACTIVE",
//		  "link_url": "https://payments-test.cashfree.com/links/P8j0fcu2o6u0",
//		  "order_splits": [],
//		  "terms_and_conditions": "",
//		  "thank_you_msg": ""
//		}

//	400
//	{
//		  "message": "bad URL, please check API documentation",
//		  "help": "Check latest errors and resolution from Merchant Dashboard API logs: https://bit.ly/4glEd0W Help Document: https://bit.ly/4eeZYO9",
//		  "code": "request_failed",
//		  "type": "invalid_request_error"
//		}

//	500
//	{
//		  "message": "internal Server Error",
//		  "help": "Check latest errors and resolution from Merchant Dashboard API logs: https://bit.ly/4glEd0W Help Document: https://bit.ly/4eeZYO9",
//		  "code": "internal_error",
//		  "type": "api_error"
//		}

//	{
//		  "cf_link_id": "1996567",
//		  "link_id": "my_link_id",
//		  "link_status": "ACTIVE",
//		  "link_currency": "INR",
//		  "link_amount": 100,
//		  "link_amount_paid": 0,
//		  "link_partial_payments": true,
//		  "link_minimum_partial_amount": 20,
//		  "link_purpose": "Payment for PlayStation 11",
//		  "link_created_at": "2021-09-30T17:05:01+05:30",
//		  "customer_details": {
//		    "customer_name": "John Doe",
//		    "customer_phone": "9999999999",
//		    "customer_email": "john@example.com"
//		  },
//		  "link_meta": {
//		    "notify_url": "https://ee08e626ecd88c61c85f5c69c0418cb5.m.pipedream.net",
//		    "upi_intent": false,
//		    "return_url": "https://www.cashfree.com/devstudio/thankyou"
//		  },
//		  "link_url": "https://payments-test.cashfree.com/links/o1tf1nvcvjhg",
//		  "link_expiry_time": "2021-10-14T15:04:05+05:30",
//		  "link_notes": {
//		    "key_1": "value_1",
//		    "key_2": "value_2"
//		  },
//		  "link_auto_reminders": true,
//		  "link_qrcode": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAFAAQMAAAD3XjfpAAAABlBMVEX///8AAABVwtN+AAAC9ElEQVR4nOyaPY7jMAyFn6HCpY7go/hmkXIzH0VHUOlC8Fvw0c4kM4PFFltJJjDAQPnSEPx5JIPbbrvttv9rC82mPW7gvnDasT5ZkSZyS/ZR6xLMANaJFQLJuj5J5mkHHgaMDpJlNfeROzAZFE4/PgKZOwZfoWTggchsDrvBd7DNZeW/+LEHUDmzY7Wn1aNHORO335JrNPBVcVPYl+2Y6/pssSS9/F6aOwDdLq9smLkBsUCl9aeNB6riymMzNwujI9QlW11hw/Lmx47AJVNBY16Zi71Ydy1JFTXU4UEGlvWYSfsGgbghXOn2HkGdgcCymdJS9GCu66HXmVtqsQwOvoSo/tnswbovLZ6mFpnHBpccTLHaPGMq5QBWKznZOpN9PbUeQcCdUV1mmFfkHmqcI4nBweZt2Ifc031SbEifrbgnMClndg8aWknJ1l5cpLlgHRp0OaZ5xiY4qRQPI5Our3mmO7Cs3L37mhp1SaYBNtRP9wwJnsll84zWYTmwJO5qOCV1CdpoT9pQkkykWWuResdccY22A4NIZIGmF2i4qesRXLBbcpWvNtwTuDCc7SWf0tTay6LVZ3ovpKOClkYHbHrZsU575LOddfiBD2XfFdhMhJozgo31NsXBFem35BoSlPjY9LkJdswkWywJEmmfWdgPeMqt64akQtr0Gj8XgKOCvubxjehmfszXaD992wH0AyL5BGZ9xqIHkU+eihRX9xkX9C2fSzKVFJtXouSITitvS8AhwSzBLm2mM6RmGoDaJfMtHvsC1YavzbD344pXxR0eNG1m8jx5yVHFNT9GBU/uEnSzDMnNSq8KLaATG0L9OiyOCfoFUn5sM7fj2gy/ztLoEtS12f6yqshc8Qj13AKFd202KHheIF2bQWseFkxKrrp8/6VLV+Au8cFNqYJYkrXZZ4s/f98zJEiPFd3gU9PPdij1/rEO6whUzsxnmug24gez6DkzOPiquAg+ztEX57rK1+WX0twBeNttt932d/sTAAD//zUdVfZhwUvzAAAAAElFTkSuQmCC",
//		  "link_notify": {
//		    "send_sms": false,
//		    "send_email": true
//		  },
//		  "order_splits": [
//		    {
//		      "vendor_id": "Jane",
//		      "percentage": 10,
//		      "tags": {
//		        "address": "Hyderabad"
//		      }
//		    },
//		    {
//		      "vendor_id": "Barbie",
//		      "percentage": 50,
//		      "tags": {
//		        "address": "Bengaluru, India"
//		      }
//		    }
//		  ]
//		}

}
