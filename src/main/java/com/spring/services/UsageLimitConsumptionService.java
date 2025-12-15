package com.spring.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.UsageLimitConsumption;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.UsageLimitConsumptionHelper;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;

@Service
public class UsageLimitConsumptionService {

	@Autowired
	private DonationHelper donationHelper;

	@Autowired
	private UsageLimitConsumptionHelper usageLimitConsumptionHelper;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public DonationRequestObject addUsageLimitConsumption(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);

		UsageLimitConsumption usageLimitConsumptionExists = usageLimitConsumptionHelper
				.getUsageLimitConsumptionBySuperadminIdAndResourceType(donationRequest.getSuperadminId(), donationRequest.getResourceType());
		if (usageLimitConsumptionExists != null) {
//			UsageLimitConsumption UsageLimitConsumption = usageLimitConsumptionHelper.getUsageLimitConsumptionByReqObj(donationRequest);
//			usageLimitConsumptionHelper.saveUsageLimitConsumption(UsageLimitConsumption);

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg(Constant.REGISTERED_SUCCESS);

			return donationRequest;
		} else {
			donationRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			donationRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);

			return donationRequest;
		}

	}

	public List<UsageLimitConsumption> getUsageLimitConsumptionListBySuperadmin(
			Request<DonationRequestObject> donationRequestObject) throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);

		List<UsageLimitConsumption> usageLimitConsumptionList = usageLimitConsumptionHelper
				.getUsageLimitConsumptionListBySuperadmin(donationRequest);
		return usageLimitConsumptionList;

	}

}
