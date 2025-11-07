package com.spring.helper;

import java.time.temporal.Temporal;
import java.util.Date;

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

	    // ✅ Extract only date part (ignores time)
	    Predicate restriction = cb.equal(
	        cb.function("date", Date.class, root.get("createdAt")),
	        cb.function("date", Date.class, cb.literal(new Date()))
	    );
	    cq.where(restriction);

	    return attendanceDetailsDao.getSession().createQuery(cq).uniqueResult();
	}


	public AttendanceDetails markPunchInAttendance(AttendanceRequestObject attendanceRequest) {
		
		AttendanceDetails attendanceDetails = new AttendanceDetails();
		
		attendanceDetails.setPunchInDateTime(new Date());
		attendanceDetails.setCreatedBy(attendanceRequest.getCreatedBy());
		attendanceDetails.setSuperadminId(attendanceRequest.getSuperadminId());
		attendanceDetails.setPunchInLocation(attendanceRequest.getPunchInLocation());
//		attendanceDetails.setPunchInImage(attendanceRequest.getPunchInImage());
		attendanceDetails.setPunchInStatus(attendanceRequest.getPunchInStatus());
		
		return attendanceDetails;
	}
	
	
	public AttendanceDetails markPunchOutAttendance(AttendanceDetails attendanceDetails, AttendanceRequestObject attendanceRequest) {
			
		attendanceDetails.setPunchOutDateTime(new Date());
		attendanceDetails.setPunchOutLocation(attendanceRequest.getPunchOutLocation());
//		attendanceDetails.setPunchOutImage(attendanceRequest.getPunchOutImage());
		attendanceDetails.setPunchOutStatus(attendanceRequest.getPunchOutStatus());
		
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
