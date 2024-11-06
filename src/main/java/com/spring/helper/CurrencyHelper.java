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
import com.spring.dao.CurrencyDetailsDao;
import com.spring.entities.CurrencyDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.CurrencyRequestObject;

@Component
public class CurrencyHelper {

	@Autowired
	private CurrencyDetailsDao currencyDetailsDao;

	public void validateCurrencyRequest(CurrencyRequestObject currencyRequest) throws BizException {
		if (currencyRequest == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public CurrencyDetails getCurrencyDetailsById(Long id) {

		CriteriaBuilder criteriaBuilder = currencyDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<CurrencyDetails> criteriaQuery = criteriaBuilder.createQuery(CurrencyDetails.class);
		Root<CurrencyDetails> root = criteriaQuery.from(CurrencyDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("id"), id);
		criteriaQuery.where(restriction);
		CurrencyDetails currencyDetails = currencyDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return currencyDetails;
	}

	public CurrencyDetails getCurrencyDetailsByReqObj(CurrencyRequestObject currencyRequest) {

		CurrencyDetails currencyDetails = new CurrencyDetails();

		currencyDetails.setCountry(currencyRequest.getCountry());
		currencyDetails.setCurrencyName(currencyRequest.getCurrencyName());
		currencyDetails.setCurrencyCode(currencyRequest.getCurrencyCode());
		currencyDetails.setUnicode(currencyRequest.getUnicode());
		currencyDetails.setHexCode(currencyRequest.getHexCode());
		currencyDetails.setHtmlCode(currencyRequest.getHtmlCode());
		currencyDetails.setCssCode(currencyRequest.getCssCode());
		currencyDetails.setCreatedAt(new Date());

		return currencyDetails;
	}

	@Transactional
	public CurrencyDetails saveCurrencyDetails(CurrencyDetails currencyDetails) {
		currencyDetailsDao.persist(currencyDetails);
		return currencyDetails;
	}

	public CurrencyDetails getUpdateCurrencyDetailsByReqObj(CurrencyRequestObject currencyRequest, CurrencyDetails currencyDetails) {

		currencyDetails.setCountry(currencyRequest.getCountry());
		currencyDetails.setCurrencyName(currencyRequest.getCurrencyName());
		currencyDetails.setCurrencyCode(currencyRequest.getCurrencyCode());
		currencyDetails.setUnicode(currencyRequest.getUnicode());
		currencyDetails.setHexCode(currencyRequest.getHexCode());
		currencyDetails.setHtmlCode(currencyRequest.getHtmlCode());
		currencyDetails.setCssCode(currencyRequest.getCssCode());

		return currencyDetails;
	}

	@Transactional
	public CurrencyDetails updateCurrencyDetails(CurrencyDetails currencyDetails) {
		currencyDetailsDao.update(currencyDetails);
		return currencyDetails;
	}

	@SuppressWarnings("unchecked")
	public List<CurrencyDetails> getCurrencyDetails() {
		List<CurrencyDetails> results = new ArrayList<>();
		results = currencyDetailsDao.getEntityManager().createQuery("SELECT CD FROM CurrencyDetails CD ")
				.getResultList();
		return results;
	}

}
