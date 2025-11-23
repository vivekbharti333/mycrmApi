package com.spring.services;

import java.time.LocalTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.AmazonApi.AmazonFaceCompare;
import com.spring.constant.Constant;
import com.spring.entities.AttendanceDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.Status;
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

		LocalTime now = LocalTime.now();
        LocalTime noon = LocalTime.NOON; 
        
        System.out.println("Details : "+attendenceRequest.getCreatedBy());
        System.out.println("Details : "+attendenceRequest.getSuperadminId());
        System.out.println("Details : "+attendenceRequest.getToken());
        System.out.println("Details : "+attendenceRequest.getRequestFor());
 
		
		UserDetails userDetails = userHelper.getUserDetailsByLoginId(attendenceRequest.getCreatedBy());
		if (userDetails != null) {
			
			System.out.println("userDetails.getUserPicture() : "+userDetails.getUserPicture());

			if (userDetails.getUserPicture() == null) {
				attendenceRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				attendenceRequest.setRespMesg("Not allow to mark attencende");
				return attendenceRequest;
			}

			attendenceRequest.setOriginalImage(userDetails.getUserPicture());
			attendenceRequest = amazonFaceCompare.amazonFaceCompare(attendenceRequest);
			
			System.out.println("Attendence : "+attendenceRequest);

			AttendanceDetails chekAttendence = attendanceHelper.getAttendanceByDate();
			if (chekAttendence != null) {
				if (chekAttendence.getPunchInStatus().equalsIgnoreCase(Status.MATCH.name())) {

					attendanceHelper.markPunchOutAttendance(chekAttendence, attendenceRequest);
					attendanceHelper.updateAttendanceDetails(chekAttendence);

					attendenceRequest.setRespCode(Constant.SUCCESS_CODE);
					attendenceRequest.setRespMesg("Mark Successfully");
				} else {
					chekAttendence.setPunchInStatus(attendenceRequest.getStatus());
					attendanceHelper.updateAttendanceDetails(chekAttendence);

					attendenceRequest.setRespCode(Constant.SUCCESS_CODE);
					attendenceRequest.setRespMesg("Mark Successfully");
				}
			} else {
				AttendanceDetails attendanceDetails = attendanceHelper.markPunchInAttendance(attendenceRequest);
				attendanceHelper.saveAttendanceDetails(attendanceDetails);

				attendenceRequest.setClickImage(null);
				attendenceRequest.setRespCode(Constant.SUCCESS_CODE);
				attendenceRequest.setRespMesg("Mark Successfully");
			}
		}
		attendenceRequest.setClickImage(null);
		attendenceRequest.setOriginalImage(null);
		return attendenceRequest;
	}

}
