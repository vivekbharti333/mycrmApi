package com.spring.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.OptionHandler;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.PaymentModeBySuperadmin;
import com.spring.entities.PaymentModeMaster;
import com.spring.entities.UserDetails;
import com.spring.enums.RequestFor;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.helper.PaymentModeHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;

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
			optionTypeDetails = optionTypeHelper.savePaymentModeDetails(optionTypeDetails);

			paymentModeRequest.setRespCode(Constant.SUCCESS_CODE);
			paymentModeRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return paymentModeRequest;
		} else {
			paymentModeRequest.setRespCode(Constant.ALREADY_EXISTS);
			paymentModeRequest.setRespMesg("Already exist");
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
