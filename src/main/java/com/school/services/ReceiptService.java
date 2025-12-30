package com.school.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.school.entities.SchoolReceipt;
import com.school.entities.SchoolReceiptDetails;
import com.school.entities.StudentDetails;
import com.school.helper.ReceiptHelper;
import com.school.object.request.SchoolReceiptDetailsRequest;
import com.school.object.request.SchoolReceiptRequest;
import com.school.object.request.StudentRequestObject;


@Service
public class ReceiptService {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private ReceiptHelper receiptHelper;

	public SchoolReceiptRequest addReceipt(Request<SchoolReceiptRequest> schoolReceiptRequest) throws BizException {
		SchoolReceiptRequest receiptRequest = schoolReceiptRequest.getPayload();
		receiptHelper.validateReceiptRequest(receiptRequest);

		SchoolReceipt existsReceipt = receiptHelper.getschoolReceiptByReceiptNumber(receiptRequest.getReceiptNumber());
		if (existsReceipt == null) {
			SchoolReceipt schoolReceipt = receiptHelper.getReceiptByReqObj(receiptRequest);
			schoolReceipt = receiptHelper.saveSchoolReceipt(schoolReceipt);

			if (receiptRequest.getReceiptDetails() != null) {
				for (SchoolReceiptDetailsRequest receiptDetailsRequest : receiptRequest.getReceiptDetails()) {
					SchoolReceiptDetails receiptDetails = receiptHelper.getReceiptDetailsByReqObj(receiptDetailsRequest, receiptRequest);

					receiptDetails = receiptHelper.saveSchoolReceiptDetails(receiptDetails);
				}
			}

			receiptRequest.setRespCode(Constant.SUCCESS_CODE);
			receiptRequest.setRespMesg(Constant.INVOICE_GEN_SUCCESS);
			return receiptRequest;
		} else {
			receiptRequest.setRespCode(Constant.ALREADY_EXISTS);
			receiptRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
			return receiptRequest;
		}
	}

	public List<SchoolReceiptRequest> getReceiptDetails(Request<SchoolReceiptRequest> schoolReceiptRequest) throws BizException {
		SchoolReceiptRequest receiptRequest = schoolReceiptRequest.getPayload();
		receiptHelper.validateReceiptRequest(receiptRequest);
		
		List<SchoolReceiptRequest> receiptList = receiptHelper.getReceiptDetails(receiptRequest);
		
		return receiptList;
	}

	

}
