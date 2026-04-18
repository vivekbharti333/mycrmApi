package com.common.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.common.services.WhatsAppService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngo.object.request.WhatsAppRequestObject;

@CrossOrigin(origins = "*")
@RestController
public class WhatsAppController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private static final String VERIFY_TOKEN = "donexia_webhook_8fK92xPqLz_2026";

	@Autowired
	private WhatsAppService whatsAppService;

	@RequestMapping(path = "addWhatsDetails", method = RequestMethod.POST)
	public Response<WhatsAppRequestObject> addWhatsDetails(@RequestBody Request<WhatsAppRequestObject> whatsAppRequestObject,
			HttpServletRequest request) {
		GenricResponse<WhatsAppRequestObject> responseObj = new GenricResponse<WhatsAppRequestObject>();
		try {
			WhatsAppRequestObject responce = whatsAppService.addWhatsDetails(whatsAppRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
//	@RequestMapping(path = "whatsAppWebhook", method = {RequestMethod.GET, RequestMethod.POST})
//	public String handleWebhook(
//	        HttpServletRequest request,
//	        @RequestParam(value = "hub.mode", required = false) String mode,
//	        @RequestParam(value = "hub.challenge", required = false) String challenge,
//	        @RequestParam(value = "hub.verify_token", required = false) String token
//	) {
//		
//		System.out.println("Enter into Whats app Webhook");
//		logger.info("Enter into Whats app Webhook");
//
//	    try {
//	        // 🔹 1. Verification (GET)
//	        if ("GET".equalsIgnoreCase(request.getMethod())) {
//	            if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
//	                return challenge;
//	            }
//	            return "Verification failed";
//	        }
//
//	        // 🔹 2. Receive Messages (POST)
//	        if ("POST".equalsIgnoreCase(request.getMethod())) {
//
//	            BufferedReader reader = request.getReader();
//	            StringBuilder payload = new StringBuilder();
//	            String line;
//
//	            while ((line = reader.readLine()) != null) {
//	                payload.append(line);
//	            }
//
//	            String json = payload.toString();
//
//	            System.out.println("Incoming Webhook: " + json);
//	            logger.info("Incoming Webhook: {}"+ json); // ✅ fixed logging
//
//	            // 🔹 Parse JSON
//	            ObjectMapper mapper = new ObjectMapper();
//	            JsonNode root = mapper.readTree(json);
//
//	            JsonNode value = root.path("entry")
//	                    .get(0)
//	                    .path("changes")
//	                    .get(0)
//	                    .path("value");
//
//	            // 🔹 Check if message exists
//	            if (value.has("messages")) {
//
//	                JsonNode messageNode = value.path("messages").get(0);
//
//	                String from = messageNode.path("from").asText();
//	                String message = messageNode.path("text").path("body").asText();
//
//	                String name = value.path("contacts")
//	                        .get(0)
//	                        .path("profile")
//	                        .path("name")
//	                        .asText();
//
//	                System.out.println("Sender: " + from);
//	                System.out.println("Name: " + name);
//	                System.out.println("Message: " + message);
//
//	                logger.info("Sender: {}"+ from);
//	                logger.info("Name: {}"+ name);
//	                logger.info("Message: {}"+ message);
//	            }
//
//	            return "EVENT_RECEIVED";
//	        }
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        logger.error("Webhook error", e);
//	    }
//
//	    return "OK";
//	}
	
	
}
