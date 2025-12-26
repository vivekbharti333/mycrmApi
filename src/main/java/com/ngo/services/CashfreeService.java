package com.ngo.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.ngo.entities.DonationDetails;
import com.ngo.helper.DonationHelper;
import com.ngo.object.request.CashfreeRequestObject;
import com.common.object.request.Request;

@Service
public class CashfreeService {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private DonationHelper donationHelper;

	public void validateCashfreeRequest(CashfreeRequestObject cashfreeRequest) throws BizException {
		if (cashfreeRequest == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	public CashfreeRequestObject updatePgPaymentStatusByCashfree(Request<CashfreeRequestObject> cashfreeRequestObject)
			throws BizException {
		CashfreeRequestObject cashfreeRequest = cashfreeRequestObject.getPayload();
		this.validateCashfreeRequest(cashfreeRequest);
		
		System.out.println(cashfreeRequest.getLinkId()+" , "+cashfreeRequest.getLink_status());

		DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo(cashfreeRequest.getLinkId());

		if (donationDetails != null) {
			donationDetails.setPaymentStatus(cashfreeRequest.getLink_status());
			donationHelper.updateDonationDetails(donationDetails);

			cashfreeRequest.setRespCode(Constant.SUCCESS_CODE);
			cashfreeRequest.setRespMesg("Successfully Submitted");
			return cashfreeRequest;
		} else {
			cashfreeRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			cashfreeRequest.setRespMesg("Something went wrong");
			return cashfreeRequest;
		}

	}

}
