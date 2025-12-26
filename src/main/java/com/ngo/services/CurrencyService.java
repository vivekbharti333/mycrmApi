package com.ngo.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.ngo.entities.CurrencyDetailsBySuperadmin;
import com.ngo.entities.CurrencyMaster;
import com.ngo.helper.CurrencyHelper;
import com.ngo.object.request.CurrencyRequestObject;
import com.common.object.request.Request;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyHelper currencyHelper;

	@Transactional
	public CurrencyRequestObject addCurrencyMaster(Request<CurrencyRequestObject> currencyRequestObject)
			throws BizException, Exception {
		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		CurrencyMaster existsCurrencyDetails = currencyHelper
				.getCurrencyMasterByCurrencyCode(currencyRequest.getCurrencyCode());

		if (existsCurrencyDetails == null) {
			CurrencyMaster currencyMaster = currencyHelper.getCurrencyMasterByReqObj(currencyRequest);
			currencyMaster = currencyHelper.saveCurrencyMaster(currencyMaster);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg("Successfully Register");
		}
		return currencyRequest;
	}

	@Transactional
	public CurrencyRequestObject updateCurrencyMaster(Request<CurrencyRequestObject> currencyRequestObject)
			throws BizException, Exception {

		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		CurrencyMaster existsCurrencyDetails = currencyHelper.getCurrencyMasterById(currencyRequest.getId());

		if (existsCurrencyDetails != null) {

			existsCurrencyDetails = currencyHelper.getUpdateCurrencyMasterByReqObj(currencyRequest, existsCurrencyDetails);
			existsCurrencyDetails = currencyHelper.updateCurrencyMaster(existsCurrencyDetails);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg(Constant.UPDATED_SUCCESS);
		}
		return currencyRequest;
	}

	public List<CurrencyMaster> getCurrencyDetails(Request<CurrencyRequestObject> currencyRequestObject)
			throws BizException, Exception {
		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		currencyRequest.setRequestedFor("CURRENCY_MASTER");
		List<CurrencyMaster> currencyList = new ArrayList<>();

		currencyList = currencyHelper.getCurrencyMaster(currencyRequest);
		return currencyList;
	}

	public CurrencyRequestObject addUpdateCurrencyBySuperadmin(Request<CurrencyRequestObject> currencyRequestObject)
			throws BizException, Exception {
		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		System.out.println(currencyRequest.getSuperadminId()+" , "+currencyRequest.getCurrencyMasterIds());
		CurrencyDetailsBySuperadmin currencyBySuperadmin = currencyHelper.getCurrencyDetailsBySuperadminById(currencyRequest.getSuperadminId());
		if (currencyBySuperadmin == null) {
			System.out.println("Enter 1");
			CurrencyDetailsBySuperadmin currencyDetailsBySuperadmin = currencyHelper.getSuperadminCurrencyByReqObj(currencyRequest);
			currencyDetailsBySuperadmin = currencyHelper.saveSuperadminCurrencyDetails(currencyDetailsBySuperadmin);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg("Successfully Register");
			return currencyRequest;
		} else {
			System.out.println("Enter 2");
			currencyBySuperadmin = currencyHelper.getUpdateSuperadminCurrencyByReqObj(currencyRequest,
					currencyBySuperadmin);
			currencyBySuperadmin = currencyHelper.updateSuperadminCurrencyDetails(currencyBySuperadmin);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return currencyRequest;
		}
	}

	public List<CurrencyMaster> getCurrencyDetailsBySuperadmin(Request<CurrencyRequestObject> currencyRequestObject)
			throws BizException, Exception {
		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		currencyRequest.setRequestedFor("BY_SUPERADMIN");
		List<CurrencyMaster> currencyList = new ArrayList<>();

		CurrencyDetailsBySuperadmin currencyBySuperadmin = currencyHelper
				.getCurrencyDetailsBySuperadminById(currencyRequest.getSuperadminId());
		currencyRequest.setCurrencyMasterIds(currencyBySuperadmin.getCurrencyMasterIds());
		currencyList = currencyHelper.getCurrencyMaster(currencyRequest);

		return currencyList;
	}

}
