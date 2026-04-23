package com.whatsapp.services;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.entities.WhatsAppMessage;
import com.common.helper.WhatsAppWebhookMessageHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngo.object.request.WhatsAppRequestObject;


@Service
public class WhatsAppWebhookMessageService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private WhatsAppWebhookMessageHelper whatsAppWebhookMessageHelper;

	@Transactional
	public void processWebhook(String json) {

	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode root = mapper.readTree(json);

	        JsonNode value = root.path("entry")
	                .get(0)
	                .path("changes")
	                .get(0)
	                .path("value");

	        	// 🔹 Handle Messages
	        if (value.has("messages")) {

	            JsonNode messageNode = value.path("messages").get(0);

	            // Convert to Request Object
	            WhatsAppRequestObject req = whatsAppWebhookMessageHelper.parseMessage(value, messageNode, json);
	            // Save
	            WhatsAppMessage whatsAppMessage = whatsAppWebhookMessageHelper.getWhatsAppMessageByReqObject(req);
	            whatsAppMessage = whatsAppWebhookMessageHelper.saveWhatsAppMessage(whatsAppMessage);
	            
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}  
	
	

}
