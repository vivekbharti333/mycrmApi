package com.ngo.object.request;


import java.util.Date;
import lombok.Data;


@Data
public class WhatsAppRequestObject {
	
	private Long id;
	private String whatsappUrl;
	private String apiKey;
	
	private String type;
	private String serviceProvider;
	private String phoneNumberId;
	private String version;
	private String userAccessToken;
	
	private String whatsAppNumber;
	private String receiptDownloadUrl;
	
	
	private String messageFrom;
	private String messageTo; 
	
	//For WhatsApp Message
	private String waId;
	private String userName;
	private String messageId;
	private String direction; // INCOMING / OUTGOING
	private String messageType; // text, image, document, template
	private String messageText;
	private String templateName;
	private String templateLanguage;
	private String mediaId;
	private String mimeType;
	private String fileName;
	private String filePath;
	private Long statusTimestamp;
	private Long messageTimestamp;
	private String contextMessageId;
	private String rawJson;
	
	private String status;	
	private String superadminId;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	
	private String requestedFor;
	private String token;
	
	private int respCode;
	private String respMesg;
	

}
