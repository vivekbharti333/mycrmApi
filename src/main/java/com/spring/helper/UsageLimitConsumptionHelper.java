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

import com.spring.dao.usageLimitConsumptionDao;
import com.spring.entities.UsageLimitConsumption;
import com.spring.object.request.DonationRequestObject;

@Component
public class UsageLimitConsumptionHelper {

	@Autowired
	private usageLimitConsumptionDao usageLimitConsumptionDao;
	
//	@Transactional
//	public boolean isUnderUsageLimit(String superadminId, String resourceType) {
//	    CriteriaBuilder cb = usageLimitConsumptionDao.getSession().getCriteriaBuilder();
//	    CriteriaQuery<UsageLimitConsumption> cq = cb.createQuery(UsageLimitConsumption.class);
//
//	    Root<UsageLimitConsumption> root = cq.from(UsageLimitConsumption.class);
//
//	    Predicate bySuperAdmin = cb.equal(root.get("superadminId"), superadminId);
//	    Predicate byResourceType = cb.equal(root.get("resourceType"), resourceType);
//	    Predicate byStatus = cb.equal(root.get("status"), "ACTIVE");
//
//	    cq.where(cb.and(bySuperAdmin, byResourceType, byStatus));
//
//	    UsageLimitConsumption record = usageLimitConsumptionDao.getSession()
//	            .createQuery(cq)
//	            .uniqueResult();
//
//	    // If not exists => NO remaining limit
//	    if (record == null) {
//	        return false; 
//	    }
//
//	    // Check consume <= limit (allowed if consume less than or equal)
//	    return record.getConsume() <= record.getLimit();
//	}
	
	
	@Transactional
	public boolean isUnderUsageLimit(String superadminId, String resourceType) {

	    CriteriaBuilder cb = usageLimitConsumptionDao.getSession().getCriteriaBuilder();
	    CriteriaQuery<UsageLimitConsumption> cq = cb.createQuery(UsageLimitConsumption.class);

	    Root<UsageLimitConsumption> root = cq.from(UsageLimitConsumption.class);

	    Predicate bySuperAdmin = cb.equal(root.get("superadminId"), superadminId);
	    Predicate byResourceType = cb.equal(root.get("resourceType"), resourceType);
	    Predicate byStatus = cb.equal(root.get("status"), "ACTIVE");

	    cq.where(cb.and(bySuperAdmin, byResourceType, byStatus));

	    UsageLimitConsumption record = usageLimitConsumptionDao.getSession()
	            .createQuery(cq)
	            .uniqueResult();

	    // 1) If record does NOT exist => return false
	    if (record == null) {
	        return false;
	    }

	    // 2) Condition: if limit_count <= consume_count => return true (limit reached)
	    return record.getLimit() <= record.getConsume();
	}


	@Transactional
	public UsageLimitConsumption getUsageLimitConsumptionBySuperadminId(String superadminId) {

		CriteriaBuilder criteriaBuilder = usageLimitConsumptionDao.getSession().getCriteriaBuilder();
		CriteriaQuery<UsageLimitConsumption> criteriaQuery = criteriaBuilder.createQuery(UsageLimitConsumption.class);
		Root<UsageLimitConsumption> root = criteriaQuery.from(UsageLimitConsumption.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction);
		UsageLimitConsumption usageLimitConsumption = usageLimitConsumptionDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return usageLimitConsumption;
	}
	
	@Transactional
	public UsageLimitConsumption getUsageLimitConsumptionBySuperadminIdAndResourceType(String superadminId, String resourceType) {

		CriteriaBuilder criteriaBuilder = usageLimitConsumptionDao.getSession().getCriteriaBuilder();
		CriteriaQuery<UsageLimitConsumption> criteriaQuery = criteriaBuilder.createQuery(UsageLimitConsumption.class);
		Root<UsageLimitConsumption> root = criteriaQuery.from(UsageLimitConsumption.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("resourceType"), resourceType);
		criteriaQuery.where(restriction1, restriction2);
		UsageLimitConsumption usageLimitConsumption = usageLimitConsumptionDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return usageLimitConsumption;
	}

	public UsageLimitConsumption getUsageLimitConsumptionByReqObj(String resourceType, String consumptionType, int limit, int consume, String superadminId) {

		UsageLimitConsumption usageLimitCons = new UsageLimitConsumption();

		usageLimitCons.setResourceType(resourceType);
		usageLimitCons.setConsumptionType(consumptionType);
		usageLimitCons.setLimit(limit);
		usageLimitCons.setConsume(consume);

		usageLimitCons.setSuperadminId(superadminId);
		usageLimitCons.setCreatedAt(new Date());

		return usageLimitCons;
	}

	@Transactional
	public UsageLimitConsumption saveUsageLimitConsumption(UsageLimitConsumption UsageLimitConsumption) {
		usageLimitConsumptionDao.persist(UsageLimitConsumption);
		return UsageLimitConsumption;
	}
	
	@Transactional
	public UsageLimitConsumption updateUsageLimitConsumption(UsageLimitConsumption UsageLimitConsumption) {
		usageLimitConsumptionDao.update(UsageLimitConsumption);
		return UsageLimitConsumption;
	}

	@SuppressWarnings("unchecked")
	public List<UsageLimitConsumption> getUsageLimitConsumptionListBySuperadmin(DonationRequestObject donationRequest) {

		List<UsageLimitConsumption> results = new ArrayList<>();

		results = usageLimitConsumptionDao.getEntityManager()
				.createQuery("SELECT DD.UsageLimitConsumption, WHERE DD.superadminId = :superadminId")
				.setParameter("superadminId", donationRequest.getSuperadminId()).getResultList();
		return results;
	}

}
