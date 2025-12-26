package com.ngo.helper;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.entities.UserDetails;
import com.common.enums.RoleType;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.common.object.request.UserRequestObject;
import com.ngo.dao.CustomerDetailsDao;
import com.ngo.entities.CustomerDetails;
import com.ngo.object.request.CustomerRequestObject;

@Component
public class CustomerHelper {

	@Autowired
	private CustomerDetailsDao customerDetailsDao;
	
	public void validateCustomerRequest(CustomerRequestObject customerRequestObject) throws BizException {
		if (customerRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public CustomerDetails getCustomerDetailsByGstNo(String gstNumber) {

		CriteriaBuilder criteriaBuilder = customerDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<CustomerDetails> criteriaQuery = criteriaBuilder.createQuery(CustomerDetails.class);
		Root<CustomerDetails> root = criteriaQuery.from(CustomerDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("gstNumber"), gstNumber);
		criteriaQuery.where(restriction);
		CustomerDetails customerDetails = customerDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return customerDetails;
	}

	public CustomerDetails getCustomerDetailsByReqObj(CustomerRequestObject customerRequest) {

		CustomerDetails customerDetails = new CustomerDetails();

		customerDetails.setLoginId(customerRequest.getMobileNo().toUpperCase());
		customerDetails.setPassword(customerRequest.getPassword());
		customerDetails.setStatus(Status.ACTIVE.name());
		customerDetails.setRoleType(RoleType.CUSTOMER.name());
		customerDetails.setFirstName(customerRequest.getFirstName());
		customerDetails.setLastName(customerRequest.getLastName());
		customerDetails.setMobileNo(customerRequest.getMobileNo());
		customerDetails.setAlternateMobile(customerRequest.getAlternateMobile());
		customerDetails.setEmailId(customerRequest.getEmailId());
		customerDetails.setCompanyName(customerRequest.getCompanyName());
		customerDetails.setGstNumber(customerRequest.getGstNumber());
		customerDetails.setCreatedBy(customerRequest.getCreatedBy());
		customerDetails.setCreatedAt(new Date());
		customerDetails.setUpdatedAt(new Date());
		customerDetails.setSuperadminId(customerRequest.getSuperadminId().toUpperCase());

		return customerDetails;
	}

	@Transactional
	public CustomerDetails saveCustomerDetails(CustomerDetails customerDetails) {
		customerDetailsDao.persist(customerDetails);
		return customerDetails;
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
	public CustomerDetails UpdateCustomerDetails(CustomerDetails customerDetails) {
		customerDetailsDao.update(customerDetails);
		return customerDetails;
	}



	@SuppressWarnings("unchecked")
	public List<CustomerDetails> getCustomerList(CustomerRequestObject customerRequest) {
		if(customerRequest.getRequestedFor().equals("ALL")) {
			String hqlQuery = "SELECT CD FROM CustomerDetails CD";
			List<CustomerDetails> results = customerDetailsDao.getEntityManager()
					.createQuery(hqlQuery)
					.getResultList();
			return results;
		}else if(customerRequest.getRequestedFor().equals("DROPDOWN")) {
			String hqlQuery = "SELECT CD FROM CustomerDetails CD";
			List<CustomerDetails> results = customerDetailsDao.getEntityManager()
					.createQuery(hqlQuery)
					.getResultList();
			return results;
		}
		return null;
		
	}
	

//	@SuppressWarnings("unchecked")
//	public List<UserDetails> getUserDetails(UserRequestObject userRequest) {
//		
//		String hqlQuery = "";
//		if(userRequest.getRequestedFor().equals("ALL")) {
//			hqlQuery = "SELECT UD FROM UserDetails UD";
//		}else if(userRequest.getRequestedFor().equals("ACTIVE")) {
//			hqlQuery = "SELECT UD FROM UserDetails UD";
//		}
//		List<UserDetails> results = userDetailsDao.getEntityManager()
//				.createQuery(hqlQuery)
//				.getResultList();
//		return results;
//	}

}
