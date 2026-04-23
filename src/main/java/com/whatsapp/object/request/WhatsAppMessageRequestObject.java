package com.whatsapp.object.request;

import java.util.List;

import lombok.Data;

@Data
public class WhatsAppMessageRequestObject {

	private String requestFor;
	private String templateId;
	private String templateLanguage;
	private String templateName;
	private String parameterFormat;
	private String headerFormat;
	private String headerText;
	private String msgBodyText;
	private List<TemplateVaribaleRequest> msgBodyVariable;
	private String footerText;
	private String language;
	private String status;
	private String category;
	private String sub_category;
	
	private String messageFrom;
	private String messageTo; 
	
	
    // 🆕 ADD THESE (but better in webhook class)
    private String phoneNumberId;
    private String displayPhoneNumber;
    private String waId;              // sender (customer)
    private String messageId;
    private String messageType;
    private Long messageTimestamp;
    private String contextMessageId;
	
	
	//make new object class and remove this value from here to that new class
	
//	private String toWhatsAppNumber;
	
}
