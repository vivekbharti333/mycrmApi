package com.spring.helper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.dao.ApiDetailsDao;
import com.spring.entities.ApiDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.APIRequestObject;

@Component
public class APIHelper {

	@Autowired
	private ApiDetailsDao apiDetailsDao;

	public void validateApiRequest(APIRequestObject apiRequestObject) throws BizException {
		if (apiRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public ApiDetails getApiDetailsByServiceProvider(String serviceProvider, String serviceFor) {

		CriteriaBuilder criteriaBuilder = apiDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<ApiDetails> criteriaQuery = criteriaBuilder.createQuery(ApiDetails.class);
		Root<ApiDetails> root = criteriaQuery.from(ApiDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("serviceProvider"), serviceProvider);
		Predicate restriction2 = criteriaBuilder.equal(root.get("serviceFor"), serviceFor);
		criteriaQuery.where(restriction1, restriction2);
		ApiDetails apiDetails = apiDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return apiDetails;
	}

	public ApiDetails getApiDetailsByReqObj(APIRequestObject apiRequest) {

		ApiDetails apiDetails = new ApiDetails();
		apiDetails.setServiceProvider(apiRequest.getServiceProvider());
		apiDetails.setServiceFor(apiRequest.getServiceFor());
		apiDetails.setUserName(apiRequest.getUserName());
		apiDetails.setApiKeys(apiRequest.getApiKeys());
		apiDetails.setApiValue(apiRequest.getApiValue());

		return apiDetails;
	}

	@Transactional
	public ApiDetails saveApiDetails(ApiDetails apiDetails) {
		apiDetailsDao.persist(apiDetails);
		return apiDetails;
	}
	
	public ApiDetails getUpdatedApiDetailsByReqObj(APIRequestObject apiRequest, ApiDetails apiDetails) {

		apiDetails.setUserName(apiRequest.getUserName());
		apiDetails.setApiKeys(apiRequest.getApiKeys());
		apiDetails.setApiValue(apiRequest.getApiValue());

		return apiDetails;
	}
	
	@Transactional
	public ApiDetails updateApiDetails(ApiDetails apiDetails) {
		apiDetailsDao.update(apiDetails);
		return apiDetails;
	}

}
