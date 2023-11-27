package com.spring.helper;

import java.util.ArrayList;
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
import com.spring.dao.ApplicationHeaderDetailsDao;
import com.spring.dao.DonationTypeDao;
import com.spring.entities.ApplicationHeaderDetails;
import com.spring.entities.DonationDetails;
import com.spring.entities.DonationType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.ApplicationRequestObject;
import com.spring.object.request.DonationRequestObject;

@Component
public class ApplicationHelper {

	@Autowired
	private ApplicationHeaderDetailsDao applicationHeaderDetailsDao;

	public void validateApplicationRequest(ApplicationRequestObject applicationRequestObject) throws BizException {
		if (applicationRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public ApplicationHeaderDetails getApplicationHeaderDetailsBySuperadminId(String superadminId) {

		CriteriaBuilder criteriaBuilder = applicationHeaderDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<ApplicationHeaderDetails> criteriaQuery = criteriaBuilder
				.createQuery(ApplicationHeaderDetails.class);
		Root<ApplicationHeaderDetails> root = criteriaQuery.from(ApplicationHeaderDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction);
		ApplicationHeaderDetails applicationHeaderDetails = applicationHeaderDetailsDao.getSession()
				.createQuery(criteriaQuery).uniqueResult();
		return applicationHeaderDetails;
	}

	public ApplicationHeaderDetails getApplicationDetailsByReqObj(ApplicationRequestObject applicationRequest) {

		ApplicationHeaderDetails applicationHeaderDetails = new ApplicationHeaderDetails();

		applicationHeaderDetails.setDisplayName(applicationRequest.getDisplayName());
		applicationHeaderDetails.setDisplayLogo(applicationRequest.getDisplayLogo());
		applicationHeaderDetails.setEmailId(applicationRequest.getEmailId());
		applicationHeaderDetails.setWebsite(applicationRequest.getWebsite());
		applicationHeaderDetails.setPhoneNumber(applicationRequest.getPhoneNumber());
		applicationHeaderDetails.setStatus(Status.ACTIVE.name());
		applicationHeaderDetails.setSuperadminId(applicationRequest.getSuperadminId());

		return applicationHeaderDetails;
	}

	@Transactional
	public ApplicationHeaderDetails saveApplicationHeaderDetails(ApplicationHeaderDetails applicationHeaderDetails) {
		applicationHeaderDetailsDao.persist(applicationHeaderDetails);
		return applicationHeaderDetails;
	}

	public ApplicationHeaderDetails getUpdatedApplicationDetailsByReqObj(ApplicationRequestObject applicationRequest, ApplicationHeaderDetails applicationHeaderDetails) {


		applicationHeaderDetails.setDisplayName(applicationRequest.getDisplayName());
		applicationHeaderDetails.setDisplayLogo(applicationRequest.getDisplayLogo());
		applicationHeaderDetails.setEmailId(applicationRequest.getEmailId());
		applicationHeaderDetails.setWebsite(applicationRequest.getWebsite());
		applicationHeaderDetails.setPhoneNumber(applicationRequest.getPhoneNumber());
		applicationHeaderDetails.setStatus(Status.ACTIVE.name());
//		applicationHeaderDetails.setSuperadminId(applicationRequest.getSuperadminId());

		return applicationHeaderDetails;
	}
	
	@Transactional
	public ApplicationHeaderDetails updateApplicationHeaderDetails(ApplicationHeaderDetails applicationHeaderDetails) {
		applicationHeaderDetailsDao.update(applicationHeaderDetails);
		return applicationHeaderDetails;
	}

	@SuppressWarnings("unchecked")
	public List<ApplicationHeaderDetails> getApplicationHeaderDetailsBySuperadminId(
			ApplicationRequestObject applicationRequest) {
		System.out.println(applicationRequest.getSuperadminId() + " suuiu");
		List<ApplicationHeaderDetails> results = new ArrayList<>();
		results = applicationHeaderDetailsDao.getEntityManager().createQuery(
				"SELECT DD FROM ApplicationHeaderDetails DD WHERE DD.status =:status AND DD.superadminId =:superadminId ORDER BY DD.id DESC")
				.setParameter("superadminId", applicationRequest.getSuperadminId())
				.setParameter("status", Status.ACTIVE.name()).getResultList();
		return results;
	}

}
