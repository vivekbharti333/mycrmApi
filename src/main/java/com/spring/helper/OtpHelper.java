package com.spring.helper;

import java.util.Date;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.dao.OtpDetailsDao;
import com.spring.entities.OtpDetails;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.UserRequestObject;

@Component
public class OtpHelper {

	@Autowired
	private OtpDetailsDao otpDetailsDao;
	
	
	public void validateOtpRequest(UserRequestObject userRequestObject) throws BizException {
		if (userRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}


	@Transactional
	public OtpDetails getOtpDetailsByMobileNo(String mobileNumber) {

		CriteriaBuilder criteriaBuilder = otpDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<OtpDetails> criteriaQuery = criteriaBuilder.createQuery(OtpDetails.class);
		Root<OtpDetails> root = criteriaQuery.from(OtpDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("mobileNumber"), mobileNumber);
		criteriaQuery.where(restriction);
		OtpDetails otpDetails = otpDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return otpDetails;
	}

	public OtpDetails getOtpDetailsByReqObj(UserRequestObject userRequest, String otp) {

		OtpDetails otpDetails = new OtpDetails();

		otpDetails.setMobileNumber(userRequest.getMobileNo());
		otpDetails.setOtp(otp);
		otpDetails.setCount(0);
		otpDetails.setStatus(Status.INIT.name());
		otpDetails.setCreatedAt(new Date());
		otpDetails.setUpdatedAt(new Date());

		return otpDetails;
	}

	@Transactional
	public OtpDetails saveOtpDetails(OtpDetails otpDetails) {
		otpDetailsDao.persist(otpDetails);
		return otpDetails;
	}
	
	@Transactional
	public OtpDetails updateOtpDetails(OtpDetails otpDetails) {
		otpDetailsDao.update(otpDetails);
		return otpDetails;
	}


	

}
