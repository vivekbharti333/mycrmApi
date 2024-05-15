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
import com.spring.dao.EnquiryDetailsDao;
import com.spring.entities.CustomerDetails;
import com.spring.entities.EnquiryDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.EnquiryRequestObject;
import com.spring.object.request.UserRequestObject;

@Component
public class EnquiryHelper {

	@Autowired
	private EnquiryDetailsDao enquiryDetailsDao;
	
	@Autowired
	private HttpServletRequest request;
	
	public void validateEnquiryRequest(EnquiryRequestObject enquiryRequestObject) throws BizException {
		if (enquiryRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}



	public EnquiryDetails getEnquiryDetailsByReqObj(EnquiryRequestObject enquiryRequest) {

		EnquiryDetails enquiryDetails = new EnquiryDetails();

		enquiryDetails.setFullName(enquiryRequest.getFullName());
		enquiryDetails.setMobileNumber(enquiryRequest.getMobileNumber());
		enquiryDetails.setEmailId(enquiryRequest.getEmailId());
		enquiryDetails.setEnquiryFor(enquiryRequest.getEnquiryFor());
		enquiryDetails.setStatus(enquiryRequest.getStatus());
		enquiryDetails.setIpAddress(request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For"): request.getRemoteAddr());
		
		enquiryDetails.setCreatedAt(new Date());

		return enquiryDetails;
	}

	@Transactional
	public EnquiryDetails saveEnquiryDetails(EnquiryDetails enquiryDetails) {
		enquiryDetailsDao.persist(enquiryDetails);
		return enquiryDetails;
	}
	



	@SuppressWarnings("unchecked")
	public List<EnquiryDetails> getEnquiryList(EnquiryRequestObject customerRequest) {
		
		String hqlQuery = "SELECT ED FROM EnquiryDetails ED";
		List<EnquiryDetails> results = enquiryDetailsDao.getEntityManager()
				.createQuery(hqlQuery)
				.getResultList();
		return results;
		
	}
	


}
