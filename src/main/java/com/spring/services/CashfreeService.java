package com.spring.services;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.object.request.CashfreeRequestObject;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.ResumeRequestObject;

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
