package com.spring.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.DonationTypeAmountDao;
import com.spring.dao.DonationTypeDao;
import com.spring.entities.DonationType;
import com.spring.entities.DonationTypeAmount;
import com.spring.enums.RequestFor;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.model.ProgramDetails;
import com.spring.object.request.DonationRequestObject;

@Component
public class DonationTypeHelper {

	@Autowired
	private DonationTypeDao donationTypeDao;
	
	@Autowired
	private DonationTypeAmountDao donationTypeAmountDao;

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
	
	@Transactional
	public DonationTypeAmount getDonationTypeAmountById(DonationRequestObject donationRequest) {
		CriteriaBuilder criteriaBuilder = donationTypeDao.getSession().getCriteriaBuilder();
		CriteriaQuery<DonationTypeAmount> criteriaQuery = criteriaBuilder.createQuery(DonationTypeAmount.class);
		Root<DonationTypeAmount> root = criteriaQuery.from(DonationTypeAmount.class);
		Predicate restriction = criteriaBuilder.equal(root.get("id"), donationRequest.getId());
		criteriaQuery.where(restriction);
		DonationTypeAmount donationTypeAmount = donationTypeDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return donationTypeAmount;
	}
	
	@Transactional
	public DonationTypeAmount getDonationTypeAmountByProgramIdCurrencyIdAndSuperadminId(DonationRequestObject donationRequest) {
		CriteriaBuilder criteriaBuilder = donationTypeDao.getSession().getCriteriaBuilder();
		CriteriaQuery<DonationTypeAmount> criteriaQuery = criteriaBuilder.createQuery(DonationTypeAmount.class);
		Root<DonationTypeAmount> root = criteriaQuery.from(DonationTypeAmount.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("programId"), donationRequest.getProgramId());
		Predicate restriction2 = criteriaBuilder.equal(root.get("currencyCode"), donationRequest.getCurrencyCode());
		Predicate restriction3 = criteriaBuilder.equal(root.get("superadminId"), donationRequest.getSuperadminId());
		criteriaQuery.where(restriction1, restriction2, restriction3);
		DonationTypeAmount donationTypeAmount = donationTypeDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return donationTypeAmount;
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

//	@SuppressWarnings("unchecked")
//	public List<DonationType> getDonationTypeListBySuperadminId(DonationRequestObject donationRequest) {
//		List<DonationType> results = new ArrayList<>();
//		if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.OPTION.name())) {
//			results = donationTypeDao.getEntityManager().createQuery(
//					"SELECT DD FROM DonationType DD WHERE DD.status =:status AND DD.superadminId =:superadminId ORDER BY DD.id DESC")
//					.setParameter("superadminId", donationRequest.getSuperadminId())
//					.setParameter("status", Status.ACTIVE.name()).getResultList();
//			return results;
//		}else {
//			results = donationTypeDao.getEntityManager().createQuery(
//					"SELECT DD FROM DonationType DD WHERE DD.superadminId =:superadminId ORDER BY DD.status, DD.id DESC")
//					.setParameter("superadminId", donationRequest.getSuperadminId())
//					.getResultList();
//			return results;
//		}
//	}
	

	
	public List<ProgramDetails> getDonationTypeListBySuperadminId(DonationRequestObject donationRequest) {
		List<DonationType> donationTypes = new ArrayList<>();
		if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.OPTION.name())) {
			donationTypes = donationTypeDao.getEntityManager().createQuery(
		            "SELECT d FROM DonationType d WHERE d.superadminId = :superadminId AND d.status =:status ORDER BY d.id DESC",
		            DonationType.class)
		            .setParameter("superadminId", donationRequest.getSuperadminId())
		            .setParameter("status", Status.ACTIVE.name())
		            .getResultList();
		} else {
			donationTypes = donationTypeDao.getEntityManager().createQuery(
		            "SELECT d FROM DonationType d WHERE d.superadminId = :superadminId ORDER BY d.id DESC",
		            DonationType.class)
		            .setParameter("superadminId", donationRequest.getSuperadminId())
		            .getResultList();
		}
	    

	    // Extract program IDs from DonationType records
	    List<Long> programIds = donationTypes.stream()
	            .map(DonationType::getId)
	            .collect(Collectors.toList());

	    // Fetch DonationTypeAmount records for the extracted program IDs
	    final Map<Long, List<DonationTypeAmount>> donationTypeAmountMap;
	    if (!programIds.isEmpty()) {
	        List<DonationTypeAmount> donationTypeAmounts = donationTypeAmountDao.getEntityManager().createQuery(
	                "SELECT da FROM DonationTypeAmount da WHERE da.programId IN :programIds",
	                DonationTypeAmount.class)
	                .setParameter("programIds", programIds)
	                .getResultList();

	        // Group DonationTypeAmount records by programId
	        donationTypeAmountMap = donationTypeAmounts.stream()
	                .collect(Collectors.groupingBy(DonationTypeAmount::getProgramId));
	    } else {
	        donationTypeAmountMap = new HashMap<>();
	    }

	    // Convert DonationType records to ProgramDetails DTO
	    return donationTypes.stream().map(donationType -> {
	        ProgramDetails programDetails = new ProgramDetails();
	        programDetails.setId(donationType.getId());
	        programDetails.setProgramName(donationType.getProgramName());
	        programDetails.setProgramAmount(donationType.getProgramAmount());
	        programDetails.setStatus(donationType.getStatus());
	        programDetails.setCreatedAt(donationType.getCreatedAt());
	        programDetails.setUpdatedAt(donationType.getUpdatedAt());
	        programDetails.setCreatedBy(donationType.getCreatedBy());
	        programDetails.setSuperadminId(donationType.getSuperadminId());

	        // Set associated DonationTypeAmount records
	        programDetails.setDonationTypeAmount(donationTypeAmountMap.getOrDefault(donationType.getId(), new ArrayList<>()));

	        return programDetails;
	    }).collect(Collectors.toList());
	}



	
	
	public DonationTypeAmount getDonationTypeAmountByReqObj(DonationRequestObject donationRequest) {

		DonationTypeAmount donationTypeAmount = new DonationTypeAmount();

		donationTypeAmount.setProgramId(donationRequest.getProgramId());
		donationTypeAmount.setProgramAmount(donationRequest.getProgramAmount());
		donationTypeAmount.setCurrencyCode(donationRequest.getCurrencyCode());
		donationTypeAmount.setStatus(Status.ACTIVE.name());

		donationTypeAmount.setCreatedBy(donationRequest.getCreatedBy());
		donationTypeAmount.setSuperadminId(donationRequest.getSuperadminId());
		donationTypeAmount.setCreatedAt(new Date());
		donationTypeAmount.setUpdatedAt(new Date());

		return donationTypeAmount;
	}

	@Transactional
	public DonationTypeAmount saveDonationTypeAmount(DonationTypeAmount donationTypeAmount) {
		donationTypeAmountDao.persist(donationTypeAmount);
		return donationTypeAmount;
	}
	
	public DonationTypeAmount getUpdatedDonationTypeAmountByReqObj(DonationRequestObject donationRequest, DonationTypeAmount donationTypeAmount) {

//		donationTypeAmount.setProgramId(donationRequest.getProgramId());
		donationTypeAmount.setProgramAmount(donationRequest.getProgramAmount());
		donationTypeAmount.setCurrencyCode(donationRequest.getCurrencyCode());
		donationTypeAmount.setStatus(Status.ACTIVE.name());

		donationTypeAmount.setCreatedBy(donationRequest.getCreatedBy());
		donationTypeAmount.setSuperadminId(donationRequest.getSuperadminId());
		donationTypeAmount.setCreatedAt(new Date());
		donationTypeAmount.setUpdatedAt(new Date());

		return donationTypeAmount;
	}

	@Transactional
	public DonationTypeAmount updateDonationTypeAmount(DonationTypeAmount donationTypeAmount) {
		donationTypeAmountDao.update(donationTypeAmount);
		return donationTypeAmount;
	}

	@SuppressWarnings("unchecked")
	public List<DonationTypeAmount> getDonationTypeAmountListBySuperadminId(DonationRequestObject donationRequest) {
		List<DonationTypeAmount> results = new ArrayList<>();
		if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.OPTION.name())) {
			results = donationTypeDao.getEntityManager().createQuery(
					"SELECT DA FROM DonationTypeAmount DA WHERE DA.programId =:programId AND DA.status =:status AND DA.currencyCode =:currencyCode AND DA.superadminId =:superadminId ORDER BY DA.id DESC")
					.setParameter("programId", donationRequest.getProgramId())
					.setParameter("currencyCode", donationRequest.getCurrencyCode())
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("status", Status.ACTIVE.name())
					.getResultList();
			return results;
		} else if(donationRequest.getRequestedFor().equalsIgnoreCase(RequestFor.ALL.name())) {
			results = donationTypeDao.getEntityManager().createQuery(
					"SELECT DA FROM DonationTypeAmount DA WHERE DA.superadminId =:superadminId ORDER BY DA.id DESC")
					.setParameter("programId", donationRequest.getProgramId())
//					.setParameter("currencyCode", donationRequest.getCurrencyCode())
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("status", Status.ACTIVE.name())
					.getResultList();
			return results;
		}
		return results;
	}

}
