package com.common.services;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.entities.WhatsAppMessage;
import com.common.helper.WhatsAppMessageHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngo.object.request.WhatsAppRequestObject;


@Service
public class WhatsAppMessageService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private WhatsAppMessageHelper whatsAppMessageHelper;

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
	            WhatsAppRequestObject req = whatsAppMessageHelper.parseMessage(value, messageNode, json);

	            // Save
	            WhatsAppMessage whatsAppMessage = whatsAppMessageHelper.getWhatsAppMessageByReqObject(req);
	            whatsAppMessage = whatsAppMessageHelper.saveWhatsAppMessage(whatsAppMessage);

	            // 🔁 Duplicate check
//	            WhatsAppMessage existing = whatsAppMessageDao.findByMessageId(req.getMessageId());
//	            if (existing != null) {
//	                return;
//	            }

//	            whatsAppMessageDao.persist(entity);

	            // 🚀 Optional: Auto reply
	            // autoReplyService.handle(req);
	        }

	        // 🔹 Handle Status Updates
//	        if (value.has("statuses")) {
//	        	whatsAppMessageHelper.updateStatus(value);
//	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}  
	
	

}
