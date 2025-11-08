package com.spring.helper;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.dao.AttendanceDetailsDao;
import com.spring.entities.AttendanceDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.AttendanceRequestObject;

@Component
public class AttendanceHelper {

	@Autowired
	private AttendanceDetailsDao attendanceDetailsDao;

	public void validateAttendanceRequest(AttendanceRequestObject attendanceRequestObject) throws BizException {
		if (attendanceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	@Transactional
	public AttendanceDetails getAttendanceByDate() {
	    CriteriaBuilder cb = attendanceDetailsDao.getSession().getCriteriaBuilder();
	    CriteriaQuery<AttendanceDetails> cq = cb.createQuery(AttendanceDetails.class);
	    Root<AttendanceDetails> root = cq.from(AttendanceDetails.class);
	    Predicate restriction = cb.equal(root.get("attendanceDate"), java.time.LocalDate.now());
	    cq.where(restriction);

	    return attendanceDetailsDao.getSession().createQuery(cq).uniqueResult();
	}

	public AttendanceDetails markPunchInAttendance(AttendanceRequestObject attendanceRequest) {
		
		AttendanceDetails attendanceDetails = new AttendanceDetails();
		
		attendanceDetails.setAttendanceDate(LocalDate.now());
		attendanceDetails.setPunchInTime(LocalTime.now());
		attendanceDetails.setCreatedBy(attendanceRequest.getCreatedBy());
		attendanceDetails.setSuperadminId(attendanceRequest.getSuperadminId());
		attendanceDetails.setLongitudeIn(attendanceRequest.getLongitudeIn());
		attendanceDetails.setLatitudeIn(attendanceRequest.getLatitudeIn());
		attendanceDetails.setPunchInLocation(attendanceRequest.getPunchInLocation());
		attendanceDetails.setPunchInImage(attendanceRequest.getClickImage());
		attendanceDetails.setPunchInStatus(attendanceRequest.getStatus());
		
		return attendanceDetails;
	}
	
	
	public AttendanceDetails markPunchOutAttendance(AttendanceDetails attendanceDetails, AttendanceRequestObject attendanceRequest) {
			
		attendanceDetails.setPunchOutTime(LocalTime.now());
		attendanceDetails.setLongitudeOut(attendanceRequest.getLongitudeOut());
		attendanceDetails.setLatitudeOut(attendanceRequest.getLatitudeOut());
		attendanceDetails.setPunchOutLocation(attendanceRequest.getPunchOutLocation());
		attendanceDetails.setPunchOutImage(attendanceRequest.getClickImage());
		attendanceDetails.setPunchOutStatus(attendanceRequest.getStatus());
		
		return attendanceDetails;
	}
	
	
	@Transactional
	public AttendanceDetails saveAttendanceDetails(AttendanceDetails attendanceDetails) {
		attendanceDetailsDao.persist(attendanceDetails);
		return attendanceDetails;
	}
	@Transactional
	public AttendanceDetails updateAttendanceDetails(AttendanceDetails attendanceDetails) {
		attendanceDetailsDao.update(attendanceDetails);
		return attendanceDetails;
	}
}
