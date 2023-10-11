package com.spring.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.DonationDetailsDao;
import com.spring.dao.OptionTypeDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.OptionTypeDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.OptionRequestObject;



@Component
public class OptionTypeHelper {
	
	@Autowired
	private OptionTypeDetailsDao optionTypeDetailsDao;
	
	
	public void validateOptionRequest(OptionRequestObject optionRequestObject) 
			throws BizException
	{ 
		if(optionRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null"); 
		}
	}
	
	@Transactional
	public OptionTypeDetails getOptionTypeDetailsBySuperadminIdAndType(String OptionType, String superadminId) {

		CriteriaBuilder criteriaBuilder = optionTypeDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<OptionTypeDetails> criteriaQuery = criteriaBuilder.createQuery(OptionTypeDetails.class);
		Root<OptionTypeDetails> root = criteriaQuery.from(OptionTypeDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("OptionType"), OptionType);
		Predicate restriction2 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1,restriction2);
		OptionTypeDetails optionTypeDetails = optionTypeDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return optionTypeDetails;
	}
	

	public OptionTypeDetails getOptionTypeDetailsByReqObj(OptionRequestObject optionRequest) {
		
		OptionTypeDetails optionTypeDetails = new OptionTypeDetails();
		
		optionTypeDetails.setOptionType(optionRequest.getOptionType());
		optionTypeDetails.setOptionFor(optionRequest.getOptionFor());
		optionTypeDetails.setStatus(Status.ACTIVE.name());
		optionTypeDetails.setCreatedBy(optionRequest.getCreatedBy());
		
		optionTypeDetails.setSuperadminId(optionRequest.getSuperadminId());
		optionTypeDetails.setCreatedAt(new Date());
		
		return optionTypeDetails;
	}
	
	@Transactional
	public OptionTypeDetails saveOptionTypeDetails(OptionTypeDetails optionTypeDetails) { 
		optionTypeDetailsDao.persist(optionTypeDetails);
		return optionTypeDetails;
	}
	


	@SuppressWarnings("unchecked")
	public List<OptionTypeDetails> getOptionTypeDetailsListBySuperadminId(OptionRequestObject optionRequest) {
		List<OptionTypeDetails> results = new ArrayList<>();
		results = optionTypeDetailsDao.getEntityManager()
				.createQuery("SELECT OT FROM OptionTypeDetails OT WHERE OT.optionFor =:optionFor OT.superadminId =:superadminId ORDER BY OT.id DESC")
				.setParameter("optionFor", optionRequest.getOptionFor())
				.setParameter("superadminId", optionRequest.getSuperadminId())
				.getResultList();
		return results;
	}


	

	
}
