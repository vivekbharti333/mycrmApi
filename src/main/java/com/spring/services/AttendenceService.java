package com.spring.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.AmazonApi.AmazonFaceCompare;
import com.spring.constant.Constant;
import com.spring.entities.AttendanceDetails;
import com.spring.entities.UserDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.AttendanceHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AttendanceRequestObject;
import com.spring.object.request.Request;

@Service
public class AttendenceService {

	@Autowired
	private AttendanceHelper attendanceHelper;

	@Autowired
	private AmazonFaceCompare amazonFaceCompare;
	
	
	

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserHelper userHelper;

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	  

	public AttendanceRequestObject markAttendance(Request<AttendanceRequestObject> attendanceRequestObject) 
		throws BizException, Exception {
		AttendanceRequestObject attendenceRequest = attendanceRequestObject.getPayload();
			attendanceHelper.validateAttendanceRequest(attendenceRequest);
			
			UserDetails userDetails = userHelper.getUserDetailsByLoginId(attendenceRequest.getCreatedBy());
			if(userDetails != null) {
				
				if(userDetails.getUserPicture() == null) {
					attendenceRequest.setRespCode(Constant.BAD_REQUEST_CODE);
					attendenceRequest.setRespMesg("Not allow to mark attencende");
					return attendenceRequest;
				}
				
				attendenceRequest =  amazonFaceCompare.amazonFaceCompare(attendenceRequest);
				 
				 
				 AttendanceDetails attendanceDetails = attendanceHelper.markPunchInAttendance(attendenceRequest);
				 attendanceHelper.saveAttendanceDetails(attendanceDetails);			}

		return attendenceRequest;
	}
	

}
