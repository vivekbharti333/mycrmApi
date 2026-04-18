package com.common.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.entities.OtpDetails;
import com.common.entities.WhatsAppDetails;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.common.helper.OtpHelper;
import com.common.helper.UserHelper;
import com.common.helper.WhatsAppHelper;
import com.common.jwt.JwtTokenUtil;
import com.common.object.request.Request;
import com.common.object.request.UserRequestObject;


@Service
public class OtpService {
	
	@Autowired
	private OtpHelper otpHelper;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private WhatsAppHelper whatsAppHelper;
	
	
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	

	
	@SuppressWarnings("static-access")
	@Transactional
	public UserRequestObject sendOtp(Request<UserRequestObject> userRequestObject) throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		otpHelper.validateOtpRequest(userRequest);

		String otp = userHelper.generateRandomChars("1234567890", 6);
		logger.info("Otp : "+otp);

		OtpDetails existsOtpDetails = otpHelper.getOtpDetailsByMobileNo(userRequest.getMobileNo());
		if (existsOtpDetails != null) {
			Date now = new Date();

			long diff = now.getTime() - existsOtpDetails.getUpdatedAt().getTime();

			// 1 day = 24 * 60 * 60 * 1000 ms
			long oneDayMs = 24L * 60 * 60 * 1000;

			if (diff < oneDayMs && existsOtpDetails.getCount() >= 2) {
			    userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			    userRequest.setRespMesg("Too many OTP requests in 24 hours. Contact admin.");
			    return userRequest;
			} else {
				existsOtpDetails.setOtp(otp);
				existsOtpDetails.setStatus(Status.INIT.name());
				existsOtpDetails.setCount(existsOtpDetails.getCount() + 1);
				existsOtpDetails.setUpdatedAt(new Date());

				// Send sms
				WhatsAppDetails whatsAppDetails = whatsAppHelper.getWhatsAppBySuperadminId(Constant.GLOBAL_SUPERADMIN_ID);
				String otpParam = whatsAppHelper.setParamForSendOtp(otp, userRequest.getMobileNo());
				whatsAppHelper.sendWhatsAppMessage(otpParam, whatsAppDetails);
				
				userRequest.setRespCode(Constant.SUCCESS_CODE);
				userRequest.setRespMesg("OTP Send on " + userRequest.getMobileNo());
				return userRequest;
			}

		} else {
			OtpDetails otpDetails = otpHelper.getOtpDetailsByReqObj(userRequest, otp);
			otpDetails = otpHelper.saveOtpDetails(otpDetails);

			// send sms
			userRequest.setRespCode(Constant.SUCCESS_CODE);
			userRequest.setRespMesg("OTP Send on " + userRequest.getMobileNo());
			return userRequest;
		}
	}
	
	
	public UserRequestObject verifyOtp(Request<UserRequestObject> userRequestObject) throws BizException, Exception {

	    UserRequestObject userRequest = userRequestObject.getPayload();
	    otpHelper.validateOtpRequest(userRequest);

	    OtpDetails existsOtpDetails = otpHelper.getOtpDetailsByMobileNo(userRequest.getMobileNo());

	    if (existsOtpDetails != null) {

	        Long dbTime = existsOtpDetails.getUpdatedAt().getTime();
	        Long nowTime = new Date().getTime();
	        Long diffTime = nowTime - dbTime;

	        System.out.println(existsOtpDetails.getOtp() + " , " + userRequest.getOtp() + " , " + diffTime);

	        // ✅ Only check expiry (5 min = 300000 ms)
	        if (diffTime <= 300000) {

	            if (existsOtpDetails.getOtp().equalsIgnoreCase(userRequest.getOtp())) {

	                existsOtpDetails.setStatus(Status.VERIFIED.name());
	                otpHelper.updateOtpDetails(existsOtpDetails);

	                userRequest.setRespCode(Constant.SUCCESS_CODE);
	                userRequest.setRespMesg("OTP Verified");

	            } else {
	                userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
	                userRequest.setRespMesg("Wrong OTP");
	            }

	        } else {
	            userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
	            userRequest.setRespMesg("OTP Expired");
	        }

	    } else {
	        userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
	        userRequest.setRespMesg("Wrong OTP");
	    }

	    return userRequest;
	}


}

