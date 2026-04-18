package com.common.helper;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.common.constant.Constant;
import com.common.dao.WhatsAppDetailsDao;
import com.common.dao.WhatsAppMessageDao;
import com.common.entities.WhatsAppDetails;
import com.common.entities.WhatsAppMessage;
import com.common.exceptions.BizException;
import com.fasterxml.jackson.databind.JsonNode;
import com.ngo.controller.LeadDetailsController;
import com.ngo.helper.DonationHelper;
import com.ngo.object.request.WhatsAppRequestObject;

@Component
public class WhatsAppMessageHelper {

    private final LeadDetailsController leadDetailsController;

//	@Autowired
//	private CustomerDetailsDao customerDetailsDao;
	
	@Autowired
	private WhatsAppMessageDao whatsAppMessageDao;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	private DonationHelper donationHelper;

    WhatsAppMessageHelper(LeadDetailsController leadDetailsController) {
        this.leadDetailsController = leadDetailsController;
    }
    
    
    public WhatsAppRequestObject parseMessage(JsonNode value, JsonNode messageNode, String rawJson) {

        WhatsAppRequestObject req = new WhatsAppRequestObject();

        String type = messageNode.path("type").asText();

        req.setMessageType(type);
        req.setMessageId(messageNode.path("id").asText());
        req.setWaId(messageNode.path("from").asText());
        req.setMessageTimestamp(messageNode.path("timestamp").asLong());

        // 👤 Name
        req.setUserName(value.path("contacts")
                .get(0)
                .path("profile")
                .path("name")
                .asText());

        // 🔁 Context
        req.setContextMessageId(messageNode.path("context").path("id").asText(null));

        // 🧾 Raw JSON
        req.setRawJson(rawJson);

        // 📌 Type Handling
        if ("text".equals(type)) {
            req.setMessageText(messageNode.path("text").path("body").asText());
        }

        if ("image".equals(type)) {
            JsonNode img = messageNode.path("image");
            req.setMediaId(img.path("id").asText());
            req.setMimeType(img.path("mime_type").asText());
        }

        if ("document".equals(type)) {
            JsonNode doc = messageNode.path("document");
            req.setMediaId(doc.path("id").asText());
            req.setMimeType(doc.path("mime_type").asText());

            String fileName = doc.path("filename").asText();
            fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            req.setFileName(fileName);
        }

        req.setDirection("INCOMING");
        req.setStatus("RECEIVED");

        return req;
    }
	
	public void validateSmsTemplateRequest(WhatsAppRequestObject whatsAppRequestObject) throws BizException {
		if (whatsAppRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	public WhatsAppMessage getWhatsAppMessageByReqObject(WhatsAppRequestObject whatsAppRequest) {

	    WhatsAppMessage msg = new WhatsAppMessage();

	    // 👤 User
	    msg.setWaId(whatsAppRequest.getWaId());
	    msg.setUserName(whatsAppRequest.getUserName());

	    // 💬 Message Identity
	    msg.setMessageId(whatsAppRequest.getMessageId());
	    msg.setDirection(whatsAppRequest.getDirection()); // INCOMING / OUTGOING
	    msg.setMessageType(whatsAppRequest.getMessageType());

	    // 📝 Text
	    msg.setMessageText(whatsAppRequest.getMessageText());

	    // 📦 Template
	    msg.setTemplateName(whatsAppRequest.getTemplateName());
	    msg.setTemplateLanguage(whatsAppRequest.getTemplateLanguage());

	    // 📎 Media
	    msg.setMediaId(whatsAppRequest.getMediaId());
	    msg.setMimeType(whatsAppRequest.getMimeType());
	    msg.setFileName(whatsAppRequest.getFileName());
	    msg.setFilePath(whatsAppRequest.getFilePath());

	    // 📊 Status
	    msg.setStatus(whatsAppRequest.getStatus());
	    msg.setStatusTimestamp(whatsAppRequest.getStatusTimestamp());

	    // ⏱ Time
	    msg.setMessageTimestamp(whatsAppRequest.getMessageTimestamp());

	    // 🔁 Context (reply)
	    msg.setContextMessageId(whatsAppRequest.getContextMessageId());

	    // 🧾 Raw JSON
	    msg.setRawJson(whatsAppRequest.getRawJson());

	    // direction default (safety)
	    if (msg.getDirection() == null) {
	        msg.setDirection("INCOMING");
	    }

	    return msg;
	}
	
	@Transactional
	public WhatsAppMessage saveWhatsAppMessage(WhatsAppMessage whatsAppMessage) {
		whatsAppMessageDao.persist(whatsAppMessage);
		return whatsAppMessage;
	}
	

}
