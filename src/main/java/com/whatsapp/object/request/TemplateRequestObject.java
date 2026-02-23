package com.whatsapp.object.request;

import java.util.List;

import lombok.Data;

@Data
public class TemplateRequestObject {
	
 
	private String requestFor;
	private String templateId;
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
	
	
	//make new object class and remove this value from here to that new class
	
	private String toWhatsAppNumber;
	


}
