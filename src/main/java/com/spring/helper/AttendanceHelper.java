package com.spring.helper;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.AddressDetailsDao;
import com.spring.dao.AttendanceDetailsDao;
import com.spring.entities.AddressDetails;
import com.spring.entities.AttendanceDetails;
import com.spring.entities.UserDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.AttendanceRequestObject;
import com.spring.object.request.UserRequestObject;

@Component
public class AttendanceHelper {

	@Autowired
	private AttendanceDetailsDao attendanceDetailsDao;

	public void validateAttendanceRequest(AttendanceRequestObject attendanceRequestObject) throws BizException {
		if (attendanceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	public AttendanceDetails markPunchInAttendance(AttendanceRequestObject attendanceRequest) {
		
		AttendanceDetails attendanceDetails = new AttendanceDetails();
		
		attendanceDetails.setPunchInDateTime(new Date());
		attendanceDetails.setCreatedBy(attendanceRequest.getCreatedBy());
		attendanceDetails.setSuperadminId(attendanceRequest.getSuperadminId());
		attendanceDetails.setPunchInLocation(attendanceRequest.getPunchInLocation());
		attendanceDetails.setPunchInImage(attendanceRequest.getPunchInImage());
		attendanceDetails.setPunchInStatus(attendanceRequest.getPunchInStatus());
		
		return attendanceDetails;
	}
	
	
	public AttendanceDetails markPunchInAttendance(AttendanceDetails attendanceDetails, AttendanceRequestObject attendanceRequest) {
			
		attendanceDetails.setPunchOutDateTime(new Date());
		attendanceDetails.setPunchOutLocation(attendanceRequest.getPunchOutLocation());
		attendanceDetails.setPunchOutImage(attendanceRequest.getPunchOutImage());
		attendanceDetails.setPunchOutStatus(attendanceRequest.getPunchOutStatus());
		
		return attendanceDetails;
	}
	
	
	@Transactional
	public AttendanceDetails saveAttendanceDetails(AttendanceDetails attendanceDetails) {
		attendanceDetailsDao.persist(attendanceDetails);
		return attendanceDetails;
	}
}
