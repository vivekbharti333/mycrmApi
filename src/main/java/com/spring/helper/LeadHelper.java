package com.spring.helper;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.LeadDetailsDao;
import com.spring.entities.LeadDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ArticleRequestObject;



@Component
public class LeadHelper {
	
	@Autowired
	private LeadDetailsDao leadDetailsDao;
	
	public void validateLeadRequest(ArticleRequestObject ArticleRequestObject) 
			throws BizException
	{ 
		if(ArticleRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null"); 
		}
	}
	
	
	@Transactional
	public LeadDetails getLeadDetailsByCustomerMobile(String customerMobile) {
		
	    CriteriaBuilder criteriaBuilder = leadDetailsDao.getSession().getCriteriaBuilder();
	    CriteriaQuery<LeadDetails> criteriaQuery = criteriaBuilder.createQuery(LeadDetails.class);
	    Root<LeadDetails> root = criteriaQuery.from(LeadDetails.class);
	    Predicate restriction = criteriaBuilder.equal(root.get("customerMobile"), customerMobile);
	    criteriaQuery.where(restriction);
	    LeadDetails leadDetails = leadDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
	    return leadDetails;
	}


	public LeadDetails getLeadDetailsByReqObj(ArticleRequestObject leadRequest) {
		
		LeadDetails leadDetails = new LeadDetails();
		
		leadDetails.setEnquirySource(leadRequest.getEnquirySource());
		leadDetails.setBusinessType(leadRequest.getBusinessType());
		leadDetails.setCustomerFirstName(leadRequest.getCustomerFirstName());
		leadDetails.setCustomerLastName(leadRequest.getCustomerLastName());
		leadDetails.setCustomerMobile(leadRequest.getCustomerMobile());
		leadDetails.setCustomerAlternateMobile(leadRequest.getCustomerAlternateMobile());
		leadDetails.setCustomerEmailId(leadRequest.getCustomerEmailId());
		leadDetails.setCustomerCity(leadRequest.getCustomerCity());
		leadDetails.setStatus(leadRequest.getStatus());
		leadDetails.setNotes(leadRequest.getNotes());
		leadDetails.setCreatedBy(leadRequest.getCreatedBy());
		leadDetails.setSuperadminId(leadRequest.getSuperadminId());
		leadDetails.setCreatedOn(new Date());
		leadDetails.setUpdatedOn(new Date());
		
		return leadDetails;
	}
	
	@Transactional
	public LeadDetails saveLeadDetails(LeadDetails leadDetails) { 
		leadDetailsDao.persist(leadDetails);
		return leadDetails;
	}


	@SuppressWarnings("unchecked")
	public List<LeadDetails> getLeadDetails(ArticleRequestObject leadRequest) {
		List<LeadDetails> results = leadDetailsDao.getEntityManager()
		.createQuery("SELECT LD FROM LeadDetails LD")
		.getResultList();
		return results;
	}

}
