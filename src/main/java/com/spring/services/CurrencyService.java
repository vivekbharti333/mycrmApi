package com.spring.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.CurrencyDetailsBySuperadmin;
import com.spring.entities.CurrencyMaster;
import com.spring.exceptions.BizException;
import com.spring.helper.CurrencyHelper;
import com.spring.object.request.CurrencyRequestObject;
import com.spring.object.request.Request;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyHelper currencyHelper;
	

	@Transactional
	public CurrencyRequestObject addUpdateCurrencyMaster(Request<CurrencyRequestObject> currencyRequestObject)
			throws BizException, Exception {
		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		CurrencyMaster existsCurrencyDetails = currencyHelper.getCurrencyMasterById(currencyRequest.getId());

		if (existsCurrencyDetails == null) {
			CurrencyMaster currencyMaster = currencyHelper.getCurrencyMasterByReqObj(currencyRequest);
			currencyMaster = currencyHelper.saveCurrencyMaster(currencyMaster);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg("Successfully Register");
			
			return currencyRequest;
		} else {

			existsCurrencyDetails = currencyHelper.getUpdateCurrencyMasterByReqObj(currencyRequest, existsCurrencyDetails);
			existsCurrencyDetails = currencyHelper.updateCurrencyMaster(existsCurrencyDetails);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return currencyRequest;
		}
	}

	public List<CurrencyMaster> getCurrencyDetails(Request<CurrencyRequestObject> currencyRequestObject) throws BizException, Exception {
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
		
		CurrencyDetailsBySuperadmin currencyBySuperadmin = currencyHelper.getCurrencyDetailsBySuperadminById(currencyRequest.getSuperadminId());
		if(currencyBySuperadmin == null) {
			
			CurrencyDetailsBySuperadmin currencyDetailsBySuperadmin = currencyHelper.getSuperadminCurrencyByReqObj(currencyRequest);
			currencyDetailsBySuperadmin = currencyHelper.saveSuperadminCurrencyDetails(currencyDetailsBySuperadmin);
			
			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg("Successfully Register");
			return currencyRequest;
		}else {
			currencyBySuperadmin = currencyHelper.getUpdateSuperadminCurrencyByReqObj(currencyRequest, currencyBySuperadmin);
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

		CurrencyDetailsBySuperadmin currencyBySuperadmin = currencyHelper.getCurrencyDetailsBySuperadminById(currencyRequest.getSuperadminId());
		currencyRequest.setCurrencyMasterIds(currencyBySuperadmin.getCurrencyMasterIds());
		currencyList = currencyHelper.getCurrencyMaster(currencyRequest);

		return currencyList;
	}

}
