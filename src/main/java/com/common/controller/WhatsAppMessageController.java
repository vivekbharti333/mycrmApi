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
import com.common.services.WhatsAppMessageService;
import com.common.services.WhatsAppService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngo.object.request.WhatsAppRequestObject;

@CrossOrigin(origins = "*")
@RestController
public class WhatsAppMessageController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private static final String VERIFY_TOKEN = "donexia_webhook_8fK92xPqLz_2026";

	@Autowired
	private WhatsAppMessageService whatsAppMessageService;

	
	@RequestMapping(path = "whatsAppWebhook", method = {RequestMethod.GET, RequestMethod.POST})
	public String handleWebhook(
	        HttpServletRequest request,
	        @RequestParam(value = "hub.mode", required = false) String mode,
	        @RequestParam(value = "hub.challenge", required = false) String challenge,
	        @RequestParam(value = "hub.verify_token", required = false) String token
	) {

	    logger.info("Webhook hit");

	    try {
	        // 🔹 Verification
	        if ("GET".equalsIgnoreCase(request.getMethod())) {
	            if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
	                return challenge;
	            }
	            return "Verification failed";
	        }

	        // 🔹 Read payload
	        BufferedReader reader = request.getReader();
	        StringBuilder payload = new StringBuilder();
	        String line;

	        while ((line = reader.readLine()) != null) {
	            payload.append(line);
	        }

	        String json = payload.toString();
	        logger.info("Incoming Webhook: {}"+ json);

	        // 🔹 Call service
	        whatsAppMessageService.processWebhook(json);

	        return "EVENT_RECEIVED";

	    } catch (Exception e) {
	        logger.error("Webhook error", e);
	    }

	    return "OK";
	}
	
	
}
