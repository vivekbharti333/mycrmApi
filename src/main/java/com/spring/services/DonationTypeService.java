package com.spring.services;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.DonationType;
import com.spring.entities.DonationTypeAmount;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationTypeHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.model.ProgramDetails;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;


@Service
public class DonationTypeService {
	
	@Autowired
	private DonationTypeHelper donationTypeHelper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Transactional
	public DonationRequestObject addDonationType(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationTypeHelper.validateDonationRequest(donationRequest);
		
//		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());

		logger.info(donationRequest.getSuperadminId()+" , "+donationRequest.getProgramName());
		
		DonationType existsDonationType = donationTypeHelper.getDonationTypeByProgramNameAndSuperadminId(donationRequest);
		if (existsDonationType == null) {
			
			DonationType donationType = donationTypeHelper.getDonationTypeByReqObj(donationRequest);
			donationType = donationTypeHelper.saveDonationType(donationType);

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Register");
			return donationRequest;
		}else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
			return donationRequest; 
		}
	}
	
	
	@Transactional
	public DonationRequestObject updateDonationType(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationTypeHelper.validateDonationRequest(donationRequest);
		
		logger.info(donationRequest.getSuperadminId()+" , "+donationRequest.getProgramName());
		
		DonationType donationType = donationTypeHelper.getDonationTypeById(donationRequest);
		if (donationType != null) {
			
			donationType = donationTypeHelper.getUpdateDonationTypeByReqObj(donationRequest, donationType);
			donationType = donationTypeHelper.updateDonationType(donationType);

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Update");
			return donationRequest;
		}else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg(Constant.DATA_NOT_FOUND);
			return donationRequest; 
		}
	}
	
	@Transactional
	public DonationRequestObject changeDonationTypeStatus(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationTypeHelper.validateDonationRequest(donationRequest);
		
		DonationType donationType = donationTypeHelper.getDonationTypeById(donationRequest);
		if (donationType != null) {
			System.out.println(donationType.getStatus());
			if(donationType.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
				donationType.setStatus(Status.ACTIVE.name());
			}else {
				donationType.setStatus(Status.INACTIVE.name());
			}
			donationType = donationTypeHelper.updateDonationType(donationType);

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Update");
			return donationRequest;
		}else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
			return donationRequest; 
		}
	}


//	public List<DonationType> getDonationTypeListBySuperadminId(Request<DonationRequestObject> donationRequestObject) {
//		DonationRequestObject donationRequest = donationRequestObject.getPayload();
//
//		List<DonationType> donationTypeList = donationTypeHelper.getDonationTypeListBySuperadminId(donationRequest);
//			return donationTypeList;
//	}
	
	public List<ProgramDetails> getDonationTypeListBySuperadminId(Request<DonationRequestObject> donationRequestObject) {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();

		List<ProgramDetails> programDetailsList = donationTypeHelper.getDonationTypeListBySuperadminId(donationRequest);
		
			return programDetailsList;

	}


		@Transactional
		public DonationRequestObject addDonationTypeAmount(Request<DonationRequestObject> donationRequestObject)
				throws BizException, Exception {
			DonationRequestObject donationRequest = donationRequestObject.getPayload();
			donationTypeHelper.validateDonationRequest(donationRequest);
			
			
			DonationTypeAmount existsDonationTypeAmount = donationTypeHelper.getDonationTypeAmountByProgramIdCurrencyIdAndSuperadminId(donationRequest);
			if (existsDonationTypeAmount == null) {
				
				DonationTypeAmount donationTypeAmount = donationTypeHelper.getDonationTypeAmountByReqObj(donationRequest);
				donationTypeAmount = donationTypeHelper.saveDonationTypeAmount(donationTypeAmount);

				donationRequest.setRespCode(Constant.SUCCESS_CODE);
				donationRequest.setRespMesg("Successfully Register");
				return donationRequest;
			}else {
				existsDonationTypeAmount = donationTypeHelper.getUpdatedDonationTypeAmountByReqObj(donationRequest, existsDonationTypeAmount);
				existsDonationTypeAmount = donationTypeHelper.updateDonationTypeAmount(existsDonationTypeAmount);
				
				donationRequest.setRespCode(Constant.SUCCESS_CODE);
				donationRequest.setRespMesg(Constant.UPDATED_SUCCESS);
				return donationRequest; 
			}
		}


		public DonationRequestObject updateDonationTypeAmount(Request<DonationRequestObject> donationRequestObject) 
			throws BizException, Exception {
				DonationRequestObject donationRequest = donationRequestObject.getPayload();
				donationTypeHelper.validateDonationRequest(donationRequest);
				
				
				DonationTypeAmount donationTypeAmount = donationTypeHelper.getDonationTypeAmountById(donationRequest);
				if (donationTypeAmount != null) {
					
					donationTypeAmount = donationTypeHelper.getUpdatedDonationTypeAmountByReqObj(donationRequest, donationTypeAmount);
					donationTypeAmount = donationTypeHelper.updateDonationTypeAmount(donationTypeAmount);

					donationRequest.setRespCode(Constant.SUCCESS_CODE);
					donationRequest.setRespMesg("Update Register");
					return donationRequest;
				}else {
					donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
					donationRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
					return donationRequest; 
				}
			}


			public List<DonationTypeAmount> getDonationTypeAmountListBySuperadminId(Request<DonationRequestObject> donationRequestObject) {
				DonationRequestObject donationRequest = donationRequestObject.getPayload();
				
				List<DonationTypeAmount> donationTypeAmountList = donationTypeHelper.getDonationTypeAmountListBySuperadminId(donationRequest);
				return donationTypeAmountList;

			}


	
	
	

}

