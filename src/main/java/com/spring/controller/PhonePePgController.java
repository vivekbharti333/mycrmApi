package com.spring.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.spring.object.request.PaymentRequestObject;
import com.spring.services.PhonePePgService;


@CrossOrigin(origins = "*")
@RestController
public class PhonePePgController {
	
	@Autowired
	private PhonePePgService phonePePgService;
	

	 @RequestMapping(path = "phonePePgResponse", method = RequestMethod.POST)
	    public String receiveResponse(@RequestBody PaymentRequestObject paymentRequestObject) {
		
	        byte[] decodedBytes = Base64.getDecoder().decode( paymentRequestObject.getResponse());
	        String decodedString = new String(decodedBytes);
	      
	        String invoiceNo = phonePePgService.updatePgResponseDetails(decodedString);
	       
	        return "Response received successfully";
	    }
	
	
}
