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
import com.spring.dao.EmailServiceDetailsDao;
import com.spring.entities.EmailServiceDetails;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.EmailServiceRequestObject;

@Component
public class EmailHelper {

	@Autowired
	private EmailServiceDetailsDao emailServiceDetailsDao;


	public void validateEmailServiceRequest(EmailServiceRequestObject emailServiceRequestObject) throws BizException {
		if (emailServiceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public EmailServiceDetails getEmailDetailsByEmailTypeAndSuperadinId(String emailType, String superadminId) {

		CriteriaBuilder criteriaBuilder = emailServiceDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<EmailServiceDetails> criteriaQuery = criteriaBuilder.createQuery(EmailServiceDetails.class);
		Root<EmailServiceDetails> root = criteriaQuery.from(EmailServiceDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("emailType"), emailType);
		Predicate restriction2 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1, restriction2);
		EmailServiceDetails optionTypeDetails = emailServiceDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return optionTypeDetails;
	}

	public EmailServiceDetails getEmailServiceDetailsByReqObj(EmailServiceRequestObject emailServiceRequest) {

		EmailServiceDetails emailServiceDetails = new EmailServiceDetails();

		emailServiceDetails.setEmailType(emailServiceRequest.getEmailType());
		emailServiceDetails.setStatus(Status.ACTIVE.name());
		emailServiceDetails.setHost(emailServiceRequest.getHost());
		emailServiceDetails.setPort(emailServiceRequest.getPort());
		emailServiceDetails.setEmailUserid(emailServiceRequest.getEmailUserid());
		emailServiceDetails.setEmailPassword(emailServiceRequest.getEmailPassword());
		emailServiceDetails.setEmailFrom(emailServiceRequest.getEmailFrom());
		emailServiceDetails.setSubject(emailServiceRequest.getSubject());
		emailServiceDetails.setEmailBody(emailServiceRequest.getEmailBody());
		emailServiceDetails.setSuperadminId(emailServiceRequest.getSuperadminId());
		emailServiceDetails.setCreatedAt(new Date());
		emailServiceDetails.setUpdatedAt(new Date());

		return emailServiceDetails;
	}

	@Transactional
	public EmailServiceDetails saveEmailServiceDetails(EmailServiceDetails emailServiceDetails) {
		emailServiceDetailsDao.persist(emailServiceDetails);
		return emailServiceDetails;
	}
	
	public EmailServiceDetails getUpdatedEmailServiceDetailsByReqObj(EmailServiceRequestObject emailServiceRequest, EmailServiceDetails emailServiceDetails) {

		emailServiceDetails.setEmailType(emailServiceRequest.getEmailType());
		emailServiceDetails.setStatus(Status.ACTIVE.name());
		emailServiceDetails.setHost(emailServiceRequest.getHost());
		emailServiceDetails.setPort(emailServiceRequest.getPort());
		emailServiceDetails.setEmailUserid(emailServiceRequest.getEmailUserid());
		emailServiceDetails.setEmailPassword(emailServiceRequest.getEmailPassword());
		emailServiceDetails.setEmailFrom(emailServiceRequest.getEmailFrom());
		emailServiceDetails.setSubject(emailServiceRequest.getSubject());
		emailServiceDetails.setEmailBody(emailServiceRequest.getEmailBody());
		emailServiceDetails.setSuperadminId(emailServiceRequest.getSuperadminId());
		emailServiceDetails.setUpdatedAt(new Date());

		return emailServiceDetails;
	}
	
	@Transactional
	public EmailServiceDetails updateEmailServiceDetails(EmailServiceDetails emailServiceDetails) {
		emailServiceDetailsDao.update(emailServiceDetails);
		return emailServiceDetails;
	}

	@SuppressWarnings("unchecked")
	public List<EmailServiceDetails> getEmailServiceDetailsList(EmailServiceRequestObject optionRequest) {
		List<EmailServiceDetails> results = new ArrayList<>();
		results = emailServiceDetailsDao.getEntityManager().createQuery(
				"SELECT PM FROM EmailServiceDetails PM WHERE PM.superadminId =:superadminId ORDER BY PM.paymentMode ASC")
				.setParameter("superadminId", optionRequest.getSuperadminId()).getResultList();
		return results;
	}
	
	
	

}
