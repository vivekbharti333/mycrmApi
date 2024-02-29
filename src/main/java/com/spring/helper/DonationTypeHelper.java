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
import com.spring.dao.DonationTypeDao;
import com.spring.entities.DonationType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;

@Component
public class DonationTypeHelper {

	@Autowired
	private DonationTypeDao donationTypeDao;

	public void validateDonationRequest(DonationRequestObject donationRequestObject) throws BizException {
		if (donationRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	@Transactional
	public DonationType getDonationTypeById(DonationRequestObject donationRequest) {
		CriteriaBuilder criteriaBuilder = donationTypeDao.getSession().getCriteriaBuilder();
		CriteriaQuery<DonationType> criteriaQuery = criteriaBuilder.createQuery(DonationType.class);
		Root<DonationType> root = criteriaQuery.from(DonationType.class);
		Predicate restriction = criteriaBuilder.equal(root.get("id"), donationRequest.getId());
		criteriaQuery.where(restriction);
		DonationType donationType = donationTypeDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return donationType;
	}

	@Transactional
	public DonationType getDonationTypeByProgramNameAndSuperadminId(DonationRequestObject donationRequest) {
		CriteriaBuilder criteriaBuilder = donationTypeDao.getSession().getCriteriaBuilder();
		CriteriaQuery<DonationType> criteriaQuery = criteriaBuilder.createQuery(DonationType.class);
		Root<DonationType> root = criteriaQuery.from(DonationType.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("programName"), donationRequest.getProgramName());
		Predicate restriction2 = criteriaBuilder.equal(root.get("superadminId"), donationRequest.getSuperadminId());
		criteriaQuery.where(restriction1, restriction2);
		DonationType donationType = donationTypeDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return donationType;
	}

	public DonationType getDonationTypeByReqObj(DonationRequestObject donationRequest) {

		DonationType donationType = new DonationType();

		donationType.setProgramName(donationRequest.getProgramName());
		donationType.setProgramAmount(donationRequest.getProgramAmount());
		donationType.setStatus(Status.ACTIVE.name());

		donationType.setCreatedBy(donationRequest.getCreatedBy());
		donationType.setSuperadminId(donationRequest.getSuperadminId());
		donationType.setCreatedAt(new Date());
		donationType.setUpdatedAt(new Date());

		return donationType;
	}

	@Transactional
	public DonationType saveDonationType(DonationType donationType) {
		donationTypeDao.persist(donationType);
		return donationType;
	}

	public DonationType getUpdateDonationTypeByReqObj(DonationRequestObject donationRequest, DonationType donationType) {

		donationType.setProgramName(donationRequest.getProgramName());
		donationType.setProgramAmount(donationRequest.getProgramAmount());

		donationType.setUpdatedAt(new Date());

		return donationType;
	}

	@Transactional
	public DonationType updateDonationType(DonationType donationType) {
		donationTypeDao.update(donationType);
		return donationType;
	}

	@SuppressWarnings("unchecked")
	public List<DonationType> getDonationTypeListBySuperadminId(DonationRequestObject donationRequest) {

		List<DonationType> results = new ArrayList<>();
		results = donationTypeDao.getEntityManager().createQuery(
				"SELECT DD FROM DonationType DD WHERE DD.status =:status AND DD.superadminId =:superadminId ORDER BY DD.id DESC")
				.setParameter("superadminId", donationRequest.getSuperadminId())
				.setParameter("status", Status.ACTIVE.name()).getResultList();
		return results;
	}

}
