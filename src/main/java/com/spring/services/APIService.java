package com.spring.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.ApiDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.APIHelper;
import com.spring.object.request.APIRequestObject;
import com.spring.object.request.Request;

@Service
public class APIService {

	@Autowired
	private APIHelper apiHelper;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public APIRequestObject addApiDetails(Request<APIRequestObject> apiRequestObject) throws BizException, Exception {
		APIRequestObject apiRequest = apiRequestObject.getPayload();
		apiHelper.validateApiRequest(apiRequest);

		ApiDetails existsApiDetails = apiHelper.getApiDetailsByServiceProvider(apiRequest.getServiceProvider(), apiRequest.getServiceFor());
		if (existsApiDetails == null) {
			ApiDetails apiDetails = apiHelper.getApiDetailsByReqObj(apiRequest);
			apiDetails = apiHelper.saveApiDetails(apiDetails);

			apiRequest.setRespCode(Constant.SUCCESS_CODE);
			apiRequest.setRespMesg("Save Successfully");

		} else {
			existsApiDetails = apiHelper.getUpdatedApiDetailsByReqObj(apiRequest, existsApiDetails);
			existsApiDetails = apiHelper.updateApiDetails(existsApiDetails);

			apiRequest.setRespCode(Constant.SUCCESS_CODE);
			apiRequest.setRespMesg("Update Successfully");
		}
		return apiRequest;
	}

}
