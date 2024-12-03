package com.spring.helper;

import java.util.*;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.dao.SubscriptionDetailsDao;
import com.spring.entities.SubscriptionDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.SubscriptionRequestObject;



@Component
public class SubscriptionHelper {
	
	@Autowired
	private SubscriptionDetailsDao subscriptionDetailsDao;
	
	
	
	public void validateSubscriptionRequest(SubscriptionRequestObject subscriptionRequest) throws BizException {
		if (subscriptionRequest == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
		
	
	@Transactional
	public SubscriptionDetails getSubscriptionDetailsBySuperadminId(String superadminId) {

		CriteriaBuilder criteriaBuilder = subscriptionDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<SubscriptionDetails> criteriaQuery = criteriaBuilder.createQuery(SubscriptionDetails.class);
		Root<SubscriptionDetails> root = criteriaQuery.from(SubscriptionDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction);
		SubscriptionDetails subscriptionDetails = subscriptionDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return subscriptionDetails;
	}

	public SubscriptionDetails getSubscriptionDetailsByReqObj(SubscriptionRequestObject subscriptionRequest) {
		
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		
		subscriptionDetails.setInvoiceNo(subscriptionRequest.getInvoiceNo());
		subscriptionDetails.setPackageName(subscriptionRequest.getPackageName());
		subscriptionDetails.setFromDate(subscriptionRequest.getFromDate());
		subscriptionDetails.setToDate(subscriptionDetails.getToDate());
		subscriptionDetails.setStatus(subscriptionRequest.getStatus());
		subscriptionDetails.setSuperadminId(subscriptionRequest.getSuperadminId());
		subscriptionDetails.setCreatedAt(new Date());
		
		return subscriptionDetails;
	}
	
	@Transactional
	public SubscriptionDetails saveSubscriptionDetails(SubscriptionDetails subscriptionDetails) { 
		subscriptionDetailsDao.persist(subscriptionDetails);
		return subscriptionDetails;
	}
	
	@Transactional
	public SubscriptionDetails updateSubscriptionDetails(SubscriptionDetails subscriptionDetails) { 
		subscriptionDetailsDao.update(subscriptionDetails);
		return subscriptionDetails;
	}
	
	

}
