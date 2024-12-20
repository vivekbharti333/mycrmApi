package com.spring.helper;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.CustomerDetailsDao;
import com.spring.dao.LeadDetailsDao;
import com.spring.entities.CustomerDetails;
import com.spring.entities.EnquiryDetails;
import com.spring.entities.LeadDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.LeadRequestObject;
import com.spring.object.request.UserRequestObject;

@Component
public class LeaddetailsHelper {

	@Autowired
	private LeadDetailsDao leadDetailsDao;
	

	
	public void validateEnquiryRequest(LeadRequestObject leadRequestObject) throws BizException {
		if (leadRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}



	public LeadDetails getLeadDetailsByReqObj(LeadRequestObject leadRequest) {

		LeadDetails leadDetails = new LeadDetails();

		leadDetails.setDonorName(leadRequest.getDonorName());
		leadDetails.setMobileNumber(leadRequest.getMobileNumber());
		leadDetails.setEmailId(leadRequest.getEmailId());
		leadDetails.setStatus(leadRequest.getStatus());
		leadDetails.setNotes(leadRequest.getNotes());
		leadDetails.setCreatedBy(leadRequest.getCreatedBy());
		leadDetails.setCreatedbyName(leadRequest.getCreatedbyName());
		leadDetails.setSuperadminId(leadRequest.getSuperadminId());
		
		leadDetails.setCreatedAt(new Date());
		leadDetails.setUpdatedAt(new Date());

		return leadDetails;
	}

	@Transactional
	public LeadDetails saveLeadDetails(LeadDetails leadDetails) {
		leadDetailsDao.persist(leadDetails);
		return leadDetails;
	}
	

	@SuppressWarnings("unchecked")
	public List<LeadDetails> getLeadList(LeadRequestObject leadRequest) {
		
		String hqlQuery = "SELECT ED FROM LeadDetails ED WHERE superadminId =:superadminId";
		List<LeadDetails> results = leadDetailsDao.getEntityManager().createQuery(hqlQuery)
				.setParameter("superadminId", leadRequest.getSuperadminId())
				.getResultList();
		return results;
		
	}
	


}
