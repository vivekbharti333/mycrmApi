package com.whatsapp.response;

import lombok.Data;

@Data
public class SendWaMessageResponse {
	
	private String messagingProduct;
	private String sendTo;
	private String waId;
	private String messagesId;
	private String messageStatus;
	
	private String errorMessage;
	private String errorType;
	private int errorCode;
	private int errorSubcode;
}
