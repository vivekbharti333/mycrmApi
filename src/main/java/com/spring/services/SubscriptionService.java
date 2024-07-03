package com.spring.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entities.SubscriptionDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.SubscriptionHelper;
import com.spring.object.request.Request;
import com.spring.object.request.SubscriptionRequestObject;
import com.spring.paymentgateway.PhonePePaymentGateway;


@Service
public class SubscriptionService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private SubscriptionHelper subscriptionHelper;

	@Autowired
	private PhonePePaymentGateway phonePePaymentGateway;



	public SubscriptionRequestObject buySubscription(Request<SubscriptionRequestObject> subscriptionRequestObject) 
			throws BizException, Exception {
		// TODO Auto-generated method stub
		SubscriptionRequestObject subscriptionRequest = subscriptionRequestObject.getPayload();
		subscriptionHelper.validateSubscriptionRequest(subscriptionRequest);
		
		SubscriptionDetails subscribeDetails = subscriptionHelper.getSubscriptionDetailsBySuperadminId(subscriptionRequest.getSuperadminId());
		if(subscribeDetails == null) {
			SubscriptionDetails subscriptionDetails = subscriptionHelper.getSubscriptionDetailsByReqObj(subscriptionRequest);
			subscriptionHelper.saveSubscriptionDetails(subscriptionDetails);
			
		}else {
			//update
		}
		//call payment gateway
		
		return subscriptionRequest;
	}
	
	

	

}
