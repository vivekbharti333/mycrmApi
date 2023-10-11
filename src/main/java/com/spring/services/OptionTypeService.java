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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.OptionTypeDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RequestFor;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.helper.OptionTypeHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.OptionRequestObject;
import com.spring.object.request.Request;


@Service
public class OptionTypeService {
	
	@Autowired
	private OptionTypeHelper optionTypeHelper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	

	



	public OptionRequestObject addOptionType(Request<OptionRequestObject> optionRequestObject) 
			throws BizException, Exception {
		OptionRequestObject optionRequest = optionRequestObject.getPayload();
		optionTypeHelper.validateOptionRequest(optionRequest);
		
		OptionTypeDetails existsOptionType = optionTypeHelper.getOptionTypeDetailsBySuperadminIdAndType(optionRequest.getOptionType(), optionRequest.getSuperadminId());
		if(existsOptionType == null) {
			OptionTypeDetails optionTypeDetails = optionTypeHelper.getOptionTypeDetailsByReqObj(optionRequest);
			optionTypeDetails = optionTypeHelper.saveOptionTypeDetails(optionTypeDetails);
			
			optionRequest.setRespCode(Constant.SUCCESS_CODE);
			optionRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return optionRequest; 
		} else {
			optionRequest.setRespCode(Constant.ALREADY_EXISTS);
			optionRequest.setRespMesg("Already exist");
			return optionRequest; 
		}
	}

	public List<OptionTypeDetails> getOptionTypeDetailsList(Request<OptionRequestObject> optionRequestObject)  {
		OptionRequestObject optionRequest = optionRequestObject.getPayload();

		List<OptionTypeDetails> optionTypeList = new ArrayList<>();
		optionTypeList = optionTypeHelper.getOptionTypeDetailsListBySuperadminId(optionRequest);

		return optionTypeList;
	}
	

}

