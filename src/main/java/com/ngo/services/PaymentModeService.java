package com.ngo.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.ngo.entities.PaymentModeBySuperadmin;
import com.ngo.entities.PaymentModeMaster;
import com.ngo.helper.PaymentModeHelper;
import com.ngo.object.request.PaymentRequestObject;

@Service
public class PaymentModeService {

	@Autowired
	private PaymentModeHelper optionTypeHelper;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public PaymentRequestObject addPaymentModeMaster(Request<PaymentRequestObject> paymentModeRequestObject)
			throws BizException, Exception {
		PaymentRequestObject paymentModeRequest = paymentModeRequestObject.getPayload();
		optionTypeHelper.validatePaymentModeRequest(paymentModeRequest);

		PaymentModeMaster existsPaymentModeDetails = optionTypeHelper.getPaymentModeMasterByPaymentMode(paymentModeRequest.getPaymentMode());
		if (existsPaymentModeDetails == null) {
			PaymentModeMaster optionTypeDetails = optionTypeHelper.getPaymentModeByReqObj(paymentModeRequest);
			optionTypeDetails = optionTypeHelper.savePaymentModeMaster(optionTypeDetails);

			paymentModeRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentModeRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return paymentModeRequest;
		} else {
			paymentModeRequest.setRespCode(Constant.ALREADY_EXISTS);
			paymentModeRequest.setRespMesg("Already exist");
			return paymentModeRequest;
		}
	}
	

	public PaymentRequestObject changeStatusOfPaymentModeMaster(Request<PaymentRequestObject> paymentModeRequestObject) 
			throws BizException, Exception {
		PaymentRequestObject paymentModeRequest = paymentModeRequestObject.getPayload();
		optionTypeHelper.validatePaymentModeRequest(paymentModeRequest);
		
		PaymentModeMaster paymentModeMaster = optionTypeHelper.getPaymentModeMasterById(paymentModeRequest.getId());
		if (paymentModeMaster != null) {
			if(paymentModeMaster.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
				paymentModeMaster.setStatus(Status.ACTIVE.name());
			}else {
				paymentModeMaster.setStatus(Status.INACTIVE.name());
			}
			
			paymentModeMaster = optionTypeHelper.updatePaymentModeMaster(paymentModeMaster);
			
			paymentModeRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentModeRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return paymentModeRequest;
		}else {
			paymentModeRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			paymentModeRequest.setRespMesg("Not Found");
			return paymentModeRequest;
		}
		
	}
	

	public List<PaymentModeMaster> getMasterPaymentModeList(Request<PaymentRequestObject> paymentModeRequestObject) {
		PaymentRequestObject paymentModeRequest = paymentModeRequestObject.getPayload();

		List<PaymentModeMaster> optionTypeList = new ArrayList<>();
		optionTypeList = optionTypeHelper.getMasterPaymentModeList(paymentModeRequest);

		return optionTypeList;
	}

	public PaymentRequestObject addPaymentModeBySuperadmin(Request<PaymentRequestObject> paymentModeRequestObject)
			throws BizException, Exception {
		PaymentRequestObject paymentModeRequest = paymentModeRequestObject.getPayload();
		optionTypeHelper.validatePaymentModeRequest(paymentModeRequest);

		PaymentModeBySuperadmin existsPaymentMode = optionTypeHelper.getPaymentModeBySuperadminId(paymentModeRequest.getSuperadminId());
		if (existsPaymentMode == null) {
			PaymentModeBySuperadmin optionTypeDetails = optionTypeHelper.getPaymentModeBySuperadminIdByReqObj(paymentModeRequest);
			optionTypeDetails = optionTypeHelper.savePaymentModeBySuperadmin(optionTypeDetails);

			paymentModeRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentModeRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return paymentModeRequest;
		} else {
			existsPaymentMode = optionTypeHelper.getUpdatePaymentModeBySuperadminIdByReqObj(paymentModeRequest, existsPaymentMode);
			existsPaymentMode = optionTypeHelper.updatePaymentModeBySuperadmin(existsPaymentMode);
			
			paymentModeRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentModeRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return paymentModeRequest;
		}
	}
	
	
	public List<PaymentModeMaster> getPaymentModeListBySuperadminId(Request<PaymentRequestObject> paymentModeRequestObject) {
		PaymentRequestObject paymentModeRequest = paymentModeRequestObject.getPayload();

		List<PaymentModeMaster> optionTypeList = new ArrayList<>();
		List<PaymentModeBySuperadmin> hi = optionTypeHelper.getPaymentModeListBySuperadminId1(paymentModeRequest);
		optionTypeList = optionTypeHelper.getPaymentModeListBySuperadminId(paymentModeRequest, hi.get(0).getPaymentModeIds());
		return optionTypeList;
	}


}
