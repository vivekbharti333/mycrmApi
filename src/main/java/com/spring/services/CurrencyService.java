package com.spring.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.CurrencyDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.CurrencyHelper;
import com.spring.object.request.CurrencyRequestObject;
import com.spring.object.request.Request;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyHelper currencyHelper;
	

	@Transactional
	public CurrencyRequestObject addUpdateCurrency(Request<CurrencyRequestObject> currencyRequestObject)
			throws BizException, Exception {
		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		CurrencyDetails existsCurrencyDetails = currencyHelper.getCurrencyDetailsById(currencyRequest.getId());

		if (existsCurrencyDetails == null) {
			CurrencyDetails currencyDetails = currencyHelper.getCurrencyDetailsByReqObj(currencyRequest);
			currencyDetails = currencyHelper.saveCurrencyDetails(currencyDetails);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg("Successfully Register");
			return currencyRequest;
		} else {

			existsCurrencyDetails = currencyHelper.getUpdateCurrencyDetailsByReqObj(currencyRequest, existsCurrencyDetails);
			existsCurrencyDetails = currencyHelper.updateCurrencyDetails(existsCurrencyDetails);

			currencyRequest.setRespCode(Constant.SUCCESS_CODE);
			currencyRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return currencyRequest;
		}
	}

	public List<CurrencyDetails> getCurrencyDetails(Request<CurrencyRequestObject> currencyRequestObject) throws BizException, Exception {
		CurrencyRequestObject currencyRequest = currencyRequestObject.getPayload();
		currencyHelper.validateCurrencyRequest(currencyRequest);

		List<CurrencyDetails> currencyList = new ArrayList<>();

		currencyList = currencyHelper.getCurrencyDetails();
		return currencyList;
	}

}
