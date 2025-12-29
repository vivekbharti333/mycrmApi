package com.school.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.school.dao.StudentDetailsDao;
import com.school.entities.StudentDetails;
import com.school.object.request.StudentRequestObject;

@Component
public class StudentHelper {
	
	@Autowired
	private StudentDetailsDao studentDetailsDao;
	
	public void validateStudentRequest(StudentRequestObject studentRequestObject) throws BizException {
		if (studentRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public StudentDetails getStudentDetailsById(Long id) {

		CriteriaBuilder criteriaBuilder = studentDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<StudentDetails> criteriaQuery = criteriaBuilder.createQuery(StudentDetails.class);
		Root<StudentDetails> root = criteriaQuery.from(StudentDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("id"), id);
		Predicate restriction2 = criteriaBuilder.notEqual(root.get("status"), Status.REMOVED.name());
		criteriaQuery.where(restriction1, restriction2);
		StudentDetails userDetails = studentDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return userDetails;
	}

	@Transactional
	public StudentDetails getStudentDetailsByAdmissionNumber(String admissionNo) {

		CriteriaBuilder criteriaBuilder = studentDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<StudentDetails> criteriaQuery = criteriaBuilder.createQuery(StudentDetails.class);
		Root<StudentDetails> root = criteriaQuery.from(StudentDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("admissionNo"), admissionNo);
		Predicate restriction2 = criteriaBuilder.notEqual(root.get("status"), Status.REMOVED.name());
		criteriaQuery.where(restriction1, restriction2);
		StudentDetails userDetails = studentDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return userDetails;
	}
	

	public StudentDetails getStudentDetailsByReqObj(StudentRequestObject studentRequest) {

	    StudentDetails studentDetails = new StudentDetails();

	    // Student Basic Details
	    studentDetails.setAdmissionNo(studentRequest.getAdmissionNo());
	    studentDetails.setRollNumber(studentRequest.getRollNumber());
	    studentDetails.setStudentPicture(studentRequest.getStudentPicture());
	    studentDetails.setGrade(studentRequest.getGrade());
	    studentDetails.setGradeSection(studentRequest.getGradeSection());
	    studentDetails.setFirstName(studentRequest.getFirstName());
	    studentDetails.setMiddleName(studentRequest.getMiddleName());
	    studentDetails.setLastName(studentRequest.getLastName());
	    studentDetails.setDob(studentRequest.getDob());
	    studentDetails.setDobPlace(studentRequest.getDobPlace());
	    studentDetails.setGender(studentRequest.getGender());
	    studentDetails.setBloodGroup(studentRequest.getBloodGroup());
	    studentDetails.setNationality(studentRequest.getNationality());
	    studentDetails.setCategory(studentRequest.getCategory());
	    studentDetails.setReligion(studentRequest.getReligion());
	    studentDetails.setAadharNumber(studentRequest.getAadharNumber());
	    studentDetails.setBirthCertificateNumber(studentRequest.getBirthCertificateNumber());
	    studentDetails.setPermanentEducationNumber(studentRequest.getPermanentEducationNumber());
	    studentDetails.setShikshaId(studentRequest.getShikshaId());
	    studentDetails.setSessionName(studentRequest.getSessionName());
	    studentDetails.setSiblingAdmissionNumber(studentRequest.getSiblingAdmissionNumber());

	    // Parent Details
	    studentDetails.setFatherName(studentRequest.getFatherName());
	    studentDetails.setFatherMobileNo(studentRequest.getFatherMobileNo());
	    studentDetails.setMotherName(studentRequest.getMotherName());
	    studentDetails.setMotherMobileNo(studentRequest.getMotherMobileNo());

	    // Current Address
	    studentDetails.setCurrentAddress(studentRequest.getCurrentAddress());
	    studentDetails.setCurrentCity(studentRequest.getCurrentCity());
	    studentDetails.setCurrentState(studentRequest.getCurrentState());
	    studentDetails.setCurrentPin(studentRequest.getCurrentPin());

	    // Permanent Address
	    studentDetails.setPermanentAddress(studentRequest.getPermanentAddress());
	    studentDetails.setPermanentCity(studentRequest.getPermanentCity());
	    studentDetails.setPermanentState(studentRequest.getPermanentState());
	    studentDetails.setPermanentPin(studentRequest.getPermanentPin());

	    // Previous School Details
	    studentDetails.setPreviousSchool(studentRequest.getPreviousSchool());
	    studentDetails.setReasonForChange(studentRequest.getReasonForChange());
	    studentDetails.setLastClassAttended(studentRequest.getLastClassAttended());

	    // Audit Fields
	    studentDetails.setStatus(Status.ACTIVE.name());
	    studentDetails.setCreatedAt(new Date());
	    studentDetails.setCreatedBy(studentRequest.getCreatedBy());
	    studentDetails.setCreatedByName(studentRequest.getCreatedByName());
	    studentDetails.setSuperadminId(studentRequest.getSuperadminId());

	    return studentDetails;
	}

	@Transactional
	public StudentDetails saveStudentDetails(StudentDetails studentDetails) {
		studentDetailsDao.persist(studentDetails);
		return studentDetails;
	}
	
	public StudentDetails getUpdatedStudentDetailsByReqObj(StudentRequestObject studentRequest, StudentDetails studentDetails) {

	    // Student Basic Details
	    studentDetails.setAdmissionNo(studentRequest.getAdmissionNo());
	    studentDetails.setRollNumber(studentRequest.getRollNumber());
	    studentDetails.setStudentPicture(studentRequest.getStudentPicture());
	    studentDetails.setGrade(studentRequest.getGrade());
	    studentDetails.setGradeSection(studentRequest.getGradeSection());
	    studentDetails.setFirstName(studentRequest.getFirstName());
	    studentDetails.setMiddleName(studentRequest.getMiddleName());
	    studentDetails.setLastName(studentRequest.getLastName());
	    studentDetails.setDob(studentRequest.getDob());
	    studentDetails.setDobPlace(studentRequest.getDobPlace());
	    studentDetails.setGender(studentRequest.getGender());
	    studentDetails.setBloodGroup(studentRequest.getBloodGroup());
	    studentDetails.setNationality(studentRequest.getNationality());
	    studentDetails.setCategory(studentRequest.getCategory());
	    studentDetails.setReligion(studentRequest.getReligion());
	    studentDetails.setAadharNumber(studentRequest.getAadharNumber());
	    studentDetails.setBirthCertificateNumber(studentRequest.getBirthCertificateNumber());
	    studentDetails.setPermanentEducationNumber(studentRequest.getPermanentEducationNumber());
	    studentDetails.setShikshaId(studentRequest.getShikshaId());
	    studentDetails.setSessionName(studentRequest.getSessionName());
	    studentDetails.setSiblingAdmissionNumber(studentRequest.getSiblingAdmissionNumber());

	    // Parent Details
	    studentDetails.setFatherName(studentRequest.getFatherName());
	    studentDetails.setFatherMobileNo(studentRequest.getFatherMobileNo());
	    studentDetails.setMotherName(studentRequest.getMotherName());
	    studentDetails.setMotherMobileNo(studentRequest.getMotherMobileNo());

	    // Current Address
	    studentDetails.setCurrentAddress(studentRequest.getCurrentAddress());
	    studentDetails.setCurrentCity(studentRequest.getCurrentCity());
	    studentDetails.setCurrentState(studentRequest.getCurrentState());
	    studentDetails.setCurrentPin(studentRequest.getCurrentPin());

	    // Permanent Address
	    studentDetails.setPermanentAddress(studentRequest.getPermanentAddress());
	    studentDetails.setPermanentCity(studentRequest.getPermanentCity());
	    studentDetails.setPermanentState(studentRequest.getPermanentState());
	    studentDetails.setPermanentPin(studentRequest.getPermanentPin());

	    // Previous School Details
	    studentDetails.setPreviousSchool(studentRequest.getPreviousSchool());
	    studentDetails.setReasonForChange(studentRequest.getReasonForChange());
	    studentDetails.setLastClassAttended(studentRequest.getLastClassAttended());

	    // Audit Fields
//	    studentDetails.setStatus(Status.ACTIVE.name());
//	    studentDetails.setCreatedAt(new Date());
//	    studentDetails.setCreatedBy(studentRequest.getCreatedBy());
//	    studentDetails.setCreatedByName(studentRequest.getCreatedByName());
//	    studentDetails.setSuperadminId(studentRequest.getSuperadminId());

	    return studentDetails;
	}

	@Transactional
	public StudentDetails updateStudentDetails(StudentDetails studentDetails) {
		studentDetailsDao.update(studentDetails);
		return studentDetails;
	}

	
	public List<StudentDetails> getStudentDetails(StudentRequestObject studentRequest) {
		
		List<StudentDetails> results = new ArrayList<>();
		String sqlQuery = "SELECT sd FROM StudentDetails sd WHERE sd.superadminId = :superadminId";

		results = studentDetailsDao.getEntityManager().createQuery(sqlQuery,StudentDetails.class)
	        .setParameter("superadminId", studentRequest.getSuperadminId())
	        .getResultList();
		
		return results;
	}




}
