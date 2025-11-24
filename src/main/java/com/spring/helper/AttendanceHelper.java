package com.spring.helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.spring.object.response.AttendanceStatusResponse;

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
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<AttendanceStatusResponse> getAttendanceReport(AttendanceRequestObject req) {

	    List<AttendanceStatusResponse> finalList = new ArrayList<>();

	    try {

	        // Convert Date → LocalDate
	        LocalDate start = req.getPunchInDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        LocalDate end = req.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	        // 1. Fetch present attendance rows
	        List<AttendanceDetails> presentList = attendanceDetailsDao.getEntityManager()
	                .createQuery("SELECT AD FROM AttendanceDetails AD WHERE AD.createdBy = :createdBy AND AD.attendanceDate BETWEEN :startDate AND :endDate ORDER BY AD.attendanceDate ASC")
	                .setParameter("createdBy", req.getCreatedBy())
	                .setParameter("startDate", start)
	                .setParameter("endDate", end)
	                .getResultList();

	        // Convert list to map for faster lookup
	        Map<LocalDate, AttendanceDetails> map = new HashMap<>();
	        for (AttendanceDetails ad : presentList) {
	            map.put(ad.getAttendanceDate(), ad);
	        }

	        // 2. Use Calendar to loop through all dates
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(req.getPunchInDateTime());

	        Calendar endCal = Calendar.getInstance();
	        endCal.setTime(req.getUpdatedAt());

	        while (!cal.after(endCal)) {

	            Date current = cal.getTime();
	            LocalDate currentLocal = current.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	            AttendanceStatusResponse res = new AttendanceStatusResponse();
	            res.setDate(current);

	            AttendanceDetails ad = map.get(currentLocal);

	            if (ad == null) {
	                // Absent record
	                res.setStatus("Absent");
	            } else {
	                // Present record
	                res.setStatus("Present");
	                res.setPunchInTime(ad.getPunchInTime() != null ? ad.getPunchInTime().toString() : null);
	                res.setPunchOutTime(ad.getPunchOutTime() != null ? ad.getPunchOutTime().toString() : null);
	                res.setPunchInLocation(ad.getPunchInLocation());
	                res.setPunchOutLocation(ad.getPunchOutLocation());
	            }

	            finalList.add(res);

	            // move next date
	            cal.add(Calendar.DATE, 1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return finalList;
	}

	
}



