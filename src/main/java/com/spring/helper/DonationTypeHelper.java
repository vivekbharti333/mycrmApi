package com.spring.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	
	
	public void validateDonationRequest(DonationRequestObject donationRequestObject) 
			throws BizException
	{ 
		if(donationRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null"); 
		}
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


	@SuppressWarnings("unchecked")
	public List<DonationType> getDonationTypeListBySuperadminId(DonationRequestObject donationRequest) {

		List<DonationType> results = new ArrayList<>();
		results = donationTypeDao.getEntityManager()
				.createQuery("SELECT DD FROM DonationType DD WHERE DD.superadminId =:superadminId ORDER BY DD.id DESC")
				.setParameter("superadminId", donationRequest.getSuperadminId())
				.getResultList();
		return results;
	}

}
