package com.spring.helper;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.UserDetailsDao;
import com.spring.entities.UserDetails;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.UserRequestObject;

@Component
public class UserHelper {

	@Autowired
	private UserDetailsDao userDetailsDao;
	
	public void validateUserRequest(UserRequestObject userRequestObject) throws BizException {
		if (userRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}
		return sb.toString();
	}

//	@Transactional
//	public UserDetails getUserDetailsByUserIdAndPassword(UserRequestObject userRequest) {
//
//		CriteriaBuilder criteriaBuilder = userDetailsDao.getSession().getCriteriaBuilder();
//		CriteriaQuery<UserDetails> criteriaQuery = criteriaBuilder.createQuery(UserDetails.class);
//		Root<UserDetails> root = criteriaQuery.from(UserDetails.class);
//		Predicate restriction = criteriaBuilder.equal(root.get("loginId"), userRequest.getLoginId());
////		restriction = criteriaBuilder.equal(root.get("password"), userRequest.getPassword());
//		criteriaQuery.where(restriction);
//		UserDetails userDetails = userDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
//		return userDetails;
//	}

	@Transactional
	public UserDetails getUserDetailsByLoginId(String loginId) {

		CriteriaBuilder criteriaBuilder = userDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<UserDetails> criteriaQuery = criteriaBuilder.createQuery(UserDetails.class);
		Root<UserDetails> root = criteriaQuery.from(UserDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("loginId"), loginId);
		criteriaQuery.where(restriction);
		UserDetails userDetails = userDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return userDetails;
	}

	public UserDetails getUserDetailsByReqObj(UserRequestObject userRequest) {

		UserDetails userDetails = new UserDetails();

		userDetails.setLoginId(userRequest.getLoginId().toUpperCase());
		userDetails.setPassword(userRequest.getPassword());
		userDetails.setStatus(Status.ACTIVE.name());
		userDetails.setRoleType(userRequest.getRoleType());
		userDetails.setFirstName(userRequest.getFirstName());
		userDetails.setLastName(userRequest.getLastName());
		userDetails.setMobileNo(userRequest.getMobileNo());
		userDetails.setAlternateMobile(userRequest.getAlternateMobile());
		userDetails.setEmailId(userRequest.getEmailId());
		userDetails.setCreatedBy(userRequest.getCreatedBy());
		userDetails.setCreatedAt(new Date());
		userDetails.setUpdatedAt(new Date());
		userDetails.setSuperadminId(userRequest.getSuperadminId().toUpperCase());

		return userDetails;
	}

	@Transactional
	public UserDetails saveUserDetails(UserDetails userDetails) {
		userDetailsDao.persist(userDetails);
		return userDetails;
	}
	

	public UserDetails getUpdatedUserDetailsByReqObj(UserRequestObject userRequest, UserDetails userDetails) {

		userDetails.setRoleType(userRequest.getRoleType());
		userDetails.setFirstName(userRequest.getFirstName());
		userDetails.setLastName(userRequest.getLastName());
		userDetails.setMobileNo(userRequest.getMobileNo());
		userDetails.setAlternateMobile(userRequest.getAlternateMobile());
		userDetails.setEmailId(userRequest.getEmailId());
		userDetails.setUpdatedAt(new Date());

		return userDetails;
	}

	@Transactional
	public UserDetails UpdateUserDetails(UserDetails userDetails) {
		userDetailsDao.update(userDetails);
		return userDetails;
	}
	

	@SuppressWarnings("unchecked")
	public List<UserDetails> getUserDetails(UserRequestObject userRequest) {
		
		String hqlQuery = "";
		if(userRequest.getRequestedFor().equals("ALL")) {
			hqlQuery = "SELECT UD FROM UserDetails UD";
		}else if(userRequest.getRequestedFor().equals("ACTIVE")) {
			hqlQuery = "SELECT UD FROM UserDetails UD";
		}
		List<UserDetails> results = userDetailsDao.getEntityManager()
				.createQuery(hqlQuery)
				.getResultList();
		return results;
	}

}
