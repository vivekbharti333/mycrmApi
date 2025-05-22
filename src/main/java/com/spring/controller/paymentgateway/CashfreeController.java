package com.spring.controller.paymentgateway;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.spring.constant.Constant;
import com.spring.entities.ApplicationHeaderDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ApplicationRequestObject;
import com.spring.object.response.GenricResponse;
import com.spring.services.ApplicationService;

@CrossOrigin(origins = "*")
	@RestController
	public class CashfreeController {


	@PostMapping("cashfreeWebhook")
	public String handleWebhook(@RequestBody String jsonString) {
		JSONObject root = new JSONObject(jsonString);

		System.out.println("== Root Level ==");
		System.out.println("Type: " + root.getString("type"));
		System.out.println("Version: " + root.getInt("version"));
		System.out.println("Event Time: " + root.getString("event_time"));

		JSONObject data = root.getJSONObject("data");

		System.out.println("\n== Payment Link Data ==");
		System.out.println("CF Link ID: " + data.getLong("cf_link_id"));
		System.out.println("Link ID: " + data.getString("link_id"));
		System.out.println("Link Status: " + data.getString("link_status"));
		System.out.println("Link Amount: " + data.getString("link_amount"));
		System.out.println("Amount Paid: " + data.getString("link_amount_paid"));
		System.out.println("Purpose: " + data.getString("link_purpose"));
		System.out.println("Created At: " + data.getString("link_created_at"));

		JSONObject customer = data.getJSONObject("customer_details");
		System.out.println("\n== Customer ==");
		System.out.println("Name: " + customer.getString("customer_name"));
		System.out.println("Email: " + customer.getString("customer_email"));
		System.out.println("Phone: " + customer.getString("customer_phone"));

		JSONObject notify = data.getJSONObject("link_notify");
		System.out.println("\n== Notifications ==");
		System.out.println("Send SMS: " + notify.getBoolean("send_sms"));
		System.out.println("Send Email: " + notify.getBoolean("send_email"));

		JSONObject order = data.getJSONObject("order");
		System.out.println("\n== Order Info ==");
		System.out.println("Order ID: " + order.getString("order_id"));
		System.out.println("Order Amount: " + order.getString("order_amount"));
		System.out.println("Transaction ID: " + order.getLong("transaction_id"));
		System.out.println("Transaction Status: " + order.getString("transaction_status"));

		return "Webhook processed using org.json";
	}
}


