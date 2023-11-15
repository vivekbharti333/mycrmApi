package com.spring.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.jvnet.staxex.util.XMLStreamReaderToXMLStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.UserDetailsDao;
import com.spring.entities.AddressDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;
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
	
	public boolean checkValidityOfUser(Date validityDate) {

		LocalDate nowDate = LocalDate.now();

		Date utilDate = validityDate;

		Instant instant = utilDate.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		long daysDifference = ChronoUnit.DAYS.between(nowDate, localDate);

		if (daysDifference >= 0) {
			return true;
		} else {
			return false;
		}
	}



	@Transactional
	public UserDetails getUserDetailsByLoginId(String loginId) {

		CriteriaBuilder criteriaBuilder = userDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<UserDetails> criteriaQuery = criteriaBuilder.createQuery(UserDetails.class);
		Root<UserDetails> root = criteriaQuery.from(UserDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("loginId"), loginId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("status"), "ACTIVE");
		criteriaQuery.where(restriction1, restriction2);
		UserDetails userDetails = userDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return userDetails;
	}
	
	@Transactional
	public UserDetails getUserDetailsByLoginIdAndSuperadminId(String loginId, String superadminId) {

		CriteriaBuilder criteriaBuilder = userDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<UserDetails> criteriaQuery = criteriaBuilder.createQuery(UserDetails.class);
		Root<UserDetails> root = criteriaQuery.from(UserDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("loginId"), loginId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1, restriction2);
		UserDetails userDetails = userDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return userDetails;
	}
	

	public UserDetails getUserDetailsByReqObj(UserRequestObject userRequest) {

		UserDetails userDetails = new UserDetails();
		
		userDetails.setUserCode(userRequest.getFirstName().substring(0,1)+userRequest.getLastName().substring(0,1));
		userDetails.setLoginId(userRequest.getMobileNo());
		userDetails.setPassword(userRequest.getPassword());
		userDetails.setStatus(Status.ACTIVE.name());
		userDetails.setRoleType(userRequest.getRoleType());
		userDetails.setService(userRequest.getService());
		userDetails.setUserPicture(userRequest.getUserPicture());
		userDetails.setFirstName(userRequest.getFirstName());
		userDetails.setLastName(userRequest.getLastName());
		userDetails.setMobileNo(userRequest.getMobileNo());
		userDetails.setAlternateMobile(userRequest.getAlternateMobile());
		userDetails.setEmailId(userRequest.getEmailId());
		userDetails.setAadharNumber(userRequest.getAadharNumber());
		userDetails.setPanNumber(userRequest.getPanNumber());
		userDetails.setCreatedBy(userRequest.getCreatedBy());
		
		userDetails.setDob(userRequest.getDob());
		userDetails.setIsPassChanged("NO");
		
		userDetails.setCreatedAt(new Date());
		userDetails.setUpdatedAt(new Date());
		if(userDetails.getRoleType().equals(RoleType.SUPERADMIN.name())) {
			userDetails.setSuperadminId(userDetails.getLoginId());
			
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, 1);
			Date oneYearLater = calendar.getTime();
			
		    userDetails.setValidityExpireOn(oneYearLater);
		}else {
			
			UserDetails existsUserDetails = this.getUserDetailsByLoginId(userRequest.getSuperadminId());
			
			userDetails.setSuperadminId(userRequest.getSuperadminId());
			userDetails.setValidityExpireOn(existsUserDetails.getValidityExpireOn());
		}
	
		return userDetails;
	}

	@Transactional
	public UserDetails saveUserDetails(UserDetails userDetails) {
		userDetailsDao.persist(userDetails);
		return userDetails;
	}
	

	public UserDetails getUpdatedUserDetailsByReqObj(UserDetails userDetails, UserRequestObject userRequest) {

		userDetails.setUserPicture(userRequest.getUserPicture());
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
		if(userRequest.getRoleType().equals(RoleType.MAINADMIN.name())) {
			List<UserDetails> results = userDetailsDao.getEntityManager()
//					.createQuery("SELECT UD FROM UserDetails UD WHERE roleType NOT IN :roleType")
					.createQuery("SELECT UD FROM UserDetails UD WHERE roleType =:roleType ORDER BY UD.id DESC")
					.setParameter("roleType", RoleType.SUPERADMIN.name())
					.getResultList();
			return results;
		}else if(userRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
			List<UserDetails> results = userDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM UserDetails UD WHERE UD.superadminId =:superadminId AND roleType NOT IN :roleType ORDER BY UD.id DESC")
					.setParameter("superadminId", userRequest.getCreatedBy())
					.setParameter("roleType", RoleType.SUPERADMIN.name())
					.getResultList();
			return results;
		}else if(userRequest.getRoleType().equals(RoleType.ADMIN.name())) {
			List<UserDetails> results = userDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM UserDetails UD WHERE UD.superadminId =:superadminId ORDER BY UD.id DESC")
					.setParameter("createdBy", userRequest.getCreatedBy())
					.setParameter("superadminId", userRequest.getSuperadminId())
					.getResultList();
			return results;
		}else if(userRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {
			List<UserDetails> results = userDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM UserDetails UD WHERE UD.createdBy =:createdBy AND UD.superadminId =:superadminId ORDER BY UD.id DESC")
					.setParameter("createdBy", userRequest.getCreatedBy())
					.setParameter("superadminId", userRequest.getSuperadminId())
					.getResultList();
			return results;
		}
		return null;	
	}
	

	public List<UserDetails> getUserDetailsByUserRole(UserRequestObject userRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public List<UserDetails> getUserListForDropDown(UserRequestObject userRequest) {
		
		List<String> excludedRoleTypes = new ArrayList<String>();
		if(userRequest.getRoleType().equalsIgnoreCase(RoleType.TEAM_LEADER.name())) {
			excludedRoleTypes = Arrays.asList(RoleType.FUNDRAISING_OFFICER.name(), RoleType.MAINADMIN.name());
			
		}else if(userRequest.getRoleType().equalsIgnoreCase(RoleType.FUNDRAISING_OFFICER.name())) {
			excludedRoleTypes = Arrays.asList(RoleType.MAINADMIN.name(), RoleType.SUPERADMIN.name(), 
					RoleType.ADMIN.name(), RoleType.TEAM_LEADER.name());
		}
		
		List<UserDetails> results = userDetailsDao.getEntityManager()
				.createQuery("SELECT UD FROM UserDetails UD WHERE roleType NOT IN :roleType AND UD.superadminId =:superadminId")
				.setParameter("roleType", excludedRoleTypes)
				.setParameter("superadminId", userRequest.getSuperadminId())
				.getResultList();
		return results;
	}
	

	@SuppressWarnings("unchecked")
	public List<AddressDetails> getAddressDetails(UserRequestObject userRequest) {
		if(userRequest.getRequestedFor().equals("ALL")) {
			List<AddressDetails> results = userDetailsDao.getEntityManager()
					.createQuery("SELECT AD FROM AddressDetails AD WHERE AD.userId =:userId And AD.superadminId =:superadminId ORDER BY AD.id DESC")
					.setParameter("userId", userRequest.getId())
					.setParameter("superadminId", userRequest.getSuperadminId())
					.getResultList();
			return results;
		}
		return null;
	}
	
	
	public Long getActiveAndInactiveUserCount(String roleType, String createdBy, String status) {
		Long count = 0L;
		if (roleType.equals(RoleType.MAINADMIN.name())) {
			count = (Long) userDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(*) FROM UserDetails DD where DD.status =:status AND roleType NOT IN :roleType")
					.setParameter("status", status)
					.setParameter("roleType", RoleType.MAINADMIN.name())
					.getSingleResult();
			return count;
		} else if (roleType.equals(RoleType.SUPERADMIN.name())) {
			count = (Long) userDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(*) FROM UserDetails DD where DD.status =:status AND roleType NOT IN :roleType AND DD.superadminId = :superadminId")
					.setParameter("status", status)
					.setParameter("roleType", RoleType.SUPERADMIN.name())
					.setParameter("superadminId", createdBy)
					.getSingleResult();
			return count;
		} else {
			count = (Long) userDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(*) FROM UserDetails DD where DD.status =:status AND DD.createdBy = :createdBy")
					.setParameter("status", status)
					.setParameter("createdBy", createdBy)
					.getSingleResult();
			return count;
		}
	}


}
