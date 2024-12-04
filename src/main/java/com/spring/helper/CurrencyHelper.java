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
import com.spring.dao.CurrencyDetailsBySuperadminDao;
import com.spring.dao.CurrencyMasterDao;
import com.spring.entities.CurrencyDetailsBySuperadmin;
import com.spring.entities.CurrencyMaster;
import com.spring.exceptions.BizException;
import com.spring.object.request.CurrencyRequestObject;

@Component
public class CurrencyHelper {

	@Autowired
	private CurrencyMasterDao currencyMasterDao;
	
	@Autowired
	private CurrencyDetailsBySuperadminDao currencyDetailsBySuperadminDao;

	public void validateCurrencyRequest(CurrencyRequestObject currencyRequest) throws BizException {
		if (currencyRequest == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public CurrencyMaster getCurrencyMasterById(Long id) {

		CriteriaBuilder criteriaBuilder = currencyMasterDao.getSession().getCriteriaBuilder();
		CriteriaQuery<CurrencyMaster> criteriaQuery = criteriaBuilder.createQuery(CurrencyMaster.class);
		Root<CurrencyMaster> root = criteriaQuery.from(CurrencyMaster.class);
		Predicate restriction = criteriaBuilder.equal(root.get("id"), id);
		criteriaQuery.where(restriction);
		CurrencyMaster currencyMaster = currencyMasterDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return currencyMaster;
	}
	
	@Transactional
	public CurrencyMaster getCurrencyMasterByCurrencyCode(String currencyCode) {

		CriteriaBuilder criteriaBuilder = currencyMasterDao.getSession().getCriteriaBuilder();
		CriteriaQuery<CurrencyMaster> criteriaQuery = criteriaBuilder.createQuery(CurrencyMaster.class);
		Root<CurrencyMaster> root = criteriaQuery.from(CurrencyMaster.class);
		Predicate restriction = criteriaBuilder.equal(root.get("currencyCode"), currencyCode);
		criteriaQuery.where(restriction);
		CurrencyMaster currencyMaster = currencyMasterDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return currencyMaster;
	}
	
	@Transactional
	public CurrencyDetailsBySuperadmin getCurrencyDetailsBySuperadminById(String superadminId) {

		CriteriaBuilder criteriaBuilder = currencyDetailsBySuperadminDao.getSession().getCriteriaBuilder();
		CriteriaQuery<CurrencyDetailsBySuperadmin> criteriaQuery = criteriaBuilder.createQuery(CurrencyDetailsBySuperadmin.class);
		Root<CurrencyDetailsBySuperadmin> root = criteriaQuery.from(CurrencyDetailsBySuperadmin.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction);
		CurrencyDetailsBySuperadmin currencyDetailsBySuperadmin = currencyDetailsBySuperadminDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return currencyDetailsBySuperadmin;
	}

	public CurrencyMaster getCurrencyMasterByReqObj(CurrencyRequestObject currencyRequest) {

		CurrencyMaster currencyMaster = new CurrencyMaster();

		currencyMaster.setCountry(currencyRequest.getCountry());
		currencyMaster.setCurrencyName(currencyRequest.getCurrencyName());
		currencyMaster.setCurrencyCode(currencyRequest.getCurrencyCode());
		currencyMaster.setUnicode(currencyRequest.getUnicode());
		currencyMaster.setHexCode(currencyRequest.getHexCode());
		currencyMaster.setHtmlCode(currencyRequest.getHtmlCode());
		currencyMaster.setCssCode(currencyRequest.getCssCode());
		currencyMaster.setCreatedAt(new Date());

		return currencyMaster;
	}

	@Transactional
	public CurrencyMaster saveCurrencyMaster(CurrencyMaster currencyMaster) {
		currencyMasterDao.persist(currencyMaster);
		return currencyMaster;
	}

	public CurrencyMaster getUpdateCurrencyMasterByReqObj(CurrencyRequestObject currencyRequest, CurrencyMaster currencyMaster) {

		currencyMaster.setCountry(currencyRequest.getCountry());
		currencyMaster.setCurrencyName(currencyRequest.getCurrencyName());
		currencyMaster.setCurrencyCode(currencyRequest.getCurrencyCode());
		currencyMaster.setUnicode(currencyRequest.getUnicode());
		currencyMaster.setHexCode(currencyRequest.getHexCode());
		currencyMaster.setHtmlCode(currencyRequest.getHtmlCode());
		currencyMaster.setCssCode(currencyRequest.getCssCode());

		return currencyMaster;
	}

	@Transactional
	public CurrencyMaster updateCurrencyMaster(CurrencyMaster currencyMaster) {
		currencyMasterDao.update(currencyMaster);
		return currencyMaster;
	}

	@SuppressWarnings("unchecked")
	public List<CurrencyMaster> getCurrencyMaster(CurrencyRequestObject currencyRequest) {
		List<CurrencyMaster> results = new ArrayList<>();
		if(currencyRequest.getRequestedFor().equalsIgnoreCase("CURRENCY_MASTER")) {
			results = currencyMasterDao.getEntityManager().createQuery("SELECT CD FROM CurrencyMaster CD ")
					.getResultList();
		}else {
			results = currencyMasterDao.getEntityManager()
					.createQuery("SELECT CD FROM CurrencyMaster CD WHERE CD.id IN  ("+currencyRequest.getCurrencyMasterIds()+")")
					.getResultList();
		}
		
		return results;
	}

	public CurrencyDetailsBySuperadmin getSuperadminCurrencyByReqObj(CurrencyRequestObject currencyRequest) {

		CurrencyDetailsBySuperadmin currencyBySuperadmin = new CurrencyDetailsBySuperadmin();

		currencyBySuperadmin.setCurrencyMasterIds(currencyRequest.getCurrencyMasterIds());
		currencyBySuperadmin.setSuperadminId(currencyRequest.getSuperadminId());

		return currencyBySuperadmin;
	}

	@Transactional
	public CurrencyDetailsBySuperadmin saveSuperadminCurrencyDetails(CurrencyDetailsBySuperadmin currencyDetails) {
		currencyDetailsBySuperadminDao.persist(currencyDetails);
		return currencyDetails;
	}
	
	public CurrencyDetailsBySuperadmin getUpdateSuperadminCurrencyByReqObj(CurrencyRequestObject currencyRequest, CurrencyDetailsBySuperadmin currencyBySuperadmin) {

		currencyBySuperadmin.setCurrencyMasterIds(currencyRequest.getCurrencyMasterIds());
		currencyBySuperadmin.setSuperadminId(currencyRequest.getSuperadminId());

		return currencyBySuperadmin;
	}
	
	@Transactional
	public CurrencyDetailsBySuperadmin updateSuperadminCurrencyDetails(CurrencyDetailsBySuperadmin currencyDetailsBySuperadmin) {
		currencyDetailsBySuperadminDao.update(currencyDetailsBySuperadmin);
		return currencyDetailsBySuperadmin;
	}

	
}
