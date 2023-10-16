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
import com.spring.dao.ResumeDetailsDao;
import com.spring.entities.ResumeDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ResumeRequestObject;



@Component
public class ResumeHelper {
	
	@Autowired
	private ResumeDetailsDao resumeDetailsDao;
	
	public void validateResumeRequest(ResumeRequestObject resumeRequestObject) 
			throws BizException
	{ 
		if(resumeRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null"); 
		}
	}
	
	
	@Transactional
	public ResumeDetails getResumeDetailsByCandidateMobile(String candidateMobile) {
		
	    CriteriaBuilder criteriaBuilder = resumeDetailsDao.getSession().getCriteriaBuilder();
	    CriteriaQuery<ResumeDetails> criteriaQuery = criteriaBuilder.createQuery(ResumeDetails.class);
	    Root<ResumeDetails> root = criteriaQuery.from(ResumeDetails.class);
	    Predicate restriction = criteriaBuilder.equal(root.get("mobileNo"), candidateMobile);
	    criteriaQuery.where(restriction);
	    ResumeDetails resumeDetails = resumeDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
	    return resumeDetails;
	}


	public ResumeDetails getResumeDetailsByReqObj(ResumeRequestObject resumeRequest) {
		
		ResumeDetails resumeDetails = new ResumeDetails();
		
		resumeDetails.setCandidatePicture(resumeRequest.getCandidatePicture());
		resumeDetails.setFirstName(resumeRequest.getFirstName());
		resumeDetails.setLastName(resumeRequest.getLastName());
		resumeDetails.setStatus(resumeRequest.getStatus());
		resumeDetails.setService(resumeRequest.getService());
		resumeDetails.setRoleType(resumeRequest.getRoleType());
		resumeDetails.setMobileNo(resumeRequest.getMobileNo());
		resumeDetails.setAlternateMobile(resumeRequest.getAlternateMobile());
		resumeDetails.setEmailId(resumeRequest.getEmailId());
		resumeDetails.setAadharNumber(resumeRequest.getAadharNumber());
		resumeDetails.setPanNumber(resumeRequest.getPanNumber());
		resumeDetails.setDob(resumeRequest.getDob());
		resumeDetails.setAddress(resumeRequest.getAddress());
		resumeDetails.setResume(resumeRequest.getResume());
		resumeDetails.setResumeUploaded(resumeRequest.getResumeUploaded());
		
		resumeDetails.setCreatedBy(resumeRequest.getCreatedBy());
		resumeDetails.setSuperadminId(resumeRequest.getSuperadminId());
		resumeDetails.setCreatedAt(new Date());
		resumeDetails.setUpdatedAt(new Date());
		
		return resumeDetails;
	}
	
	@Transactional
	public ResumeDetails saveResumeDetails(ResumeDetails resumeDetails) { 
		resumeDetailsDao.persist(resumeDetails);
		return resumeDetails;
	}


	@SuppressWarnings("unchecked")
	public List<ResumeDetails> getResumeDetails(ResumeRequestObject resumeRequest) {
		List<ResumeDetails> results = resumeDetailsDao.getEntityManager()
		.createQuery("SELECT RD FROM ResumeDetails RD")
		.getResultList();
		return results;
	}

}
