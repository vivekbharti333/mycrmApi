package com.school.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.school.dao.SchoolReceiptDao;
import com.school.dao.SchoolReceiptDetailsDao;
import com.school.entities.SchoolReceipt;
import com.school.entities.SchoolReceiptDetails;
import com.school.entities.StudentDetails;
import com.school.object.request.SchoolReceiptDetailsRequest;
import com.school.object.request.SchoolReceiptRequest;


@Component
public class ReceiptHelper {

	@Autowired
	private SchoolReceiptDao schoolReceiptDao;
	
	@Autowired
	private SchoolReceiptDetailsDao schoolReceiptDetailsDao;

	public void validateReceiptRequest(SchoolReceiptRequest schoolReceiptRequest) throws BizException {
		if (schoolReceiptRequest == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public SchoolReceipt getschoolReceiptByReceiptNumber(String receiptNumber) {

		CriteriaBuilder criteriaBuilder = schoolReceiptDao.getSession().getCriteriaBuilder();
		CriteriaQuery<SchoolReceipt> criteriaQuery = criteriaBuilder.createQuery(SchoolReceipt.class);
		Root<SchoolReceipt> root = criteriaQuery.from(SchoolReceipt.class);
		Predicate restriction = criteriaBuilder.equal(root.get("receiptNumber"), receiptNumber);
		criteriaQuery.where(restriction);
		SchoolReceipt schoolReceipt = schoolReceiptDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return schoolReceipt;
	}

	public SchoolReceipt getReceiptByReqObj(SchoolReceiptRequest receiptRequest) {
		
		SchoolReceipt receipt = new SchoolReceipt();

		receipt.setReceiptNumber(receiptRequest.getReceiptNumber());
		receipt.setAdmissionNo(receiptRequest.getAdmissionNo());
		receipt.setRollNumber(receiptRequest.getRollNumber());
		receipt.setStudentName(receiptRequest.getStudentName());
		receipt.setGrade(receiptRequest.getGrade());
		receipt.setGradeSection(receiptRequest.getGradeSection());
		receipt.setAcademicSession(receiptRequest.getAcademicSession());
		receipt.setInstallmentName(receiptRequest.getInstallmentName());
		receipt.setPaymentMode(receiptRequest.getPaymentMode());
		receipt.setPaymentDate(receiptRequest.getPaymentDate());

		receipt.setTotalAmount(receiptRequest.getTotalAmount());
		receipt.setDiscountAmount(receiptRequest.getDiscountAmount());
		receipt.setFineAmount(receiptRequest.getFineAmount());
		receipt.setNetAmount(receiptRequest.getNetAmount());
		receipt.setStatus(receiptRequest.getStatus());

		// Audit Fields
		receipt.setCreatedAt(receiptRequest.getCreatedAt() != null ? receiptRequest.getCreatedAt() : new Date());
		receipt.setCreatedBy(receiptRequest.getCreatedBy());
		receipt.setCreatedByName(receiptRequest.getCreatedByName());
		receipt.setSuperadminId(receiptRequest.getSuperadminId());

		return receipt;
	}

	@Transactional
	public SchoolReceipt saveSchoolReceipt(SchoolReceipt schoolReceipt) {
		schoolReceiptDao.persist(schoolReceipt);
		return schoolReceipt;
	}

	public SchoolReceiptDetails getReceiptDetailsByReqObj(SchoolReceiptDetailsRequest receiptDetailsRequest,
			SchoolReceiptRequest receiptRequest) {

		SchoolReceiptDetails schoolReceiptDetails = new SchoolReceiptDetails();

		schoolReceiptDetails.setReceiptNumber(receiptRequest.getReceiptNumber());
		schoolReceiptDetails.setStudentName(receiptRequest.getStudentName());
		schoolReceiptDetails.setFeeType(receiptDetailsRequest.getFeeType());
		schoolReceiptDetails.setAmount(receiptDetailsRequest.getAmount());

		// Audit fields (optional but recommended)
		schoolReceiptDetails.setCreatedAt(new Date());
		schoolReceiptDetails.setCreatedBy(receiptRequest.getCreatedBy());
		schoolReceiptDetails.setCreatedByName(receiptRequest.getCreatedByName());
		schoolReceiptDetails.setSuperadminId(receiptRequest.getSuperadminId());

		return schoolReceiptDetails;
	}
	
	@Transactional
	public SchoolReceiptDetails saveSchoolReceiptDetails(SchoolReceiptDetails schoolReceiptDetails) {
		schoolReceiptDetailsDao.persist(schoolReceiptDetails);
		return schoolReceiptDetails;
	}

//	public List<SchoolReceiptRequest> getReceiptDetails(SchoolReceiptRequest receiptRequest) {
//		List<SchoolReceiptRequest> results = new ArrayList<>();
//		String sqlQuery = "SELECT SR FROM SchoolReceipt SR WHERE SR.superadminId = :superadminId, SR.academicSession = :academicSession, SR.grade = :grade, SR.gradeSection = :gradeSection, SR.studentName = :studentName, SR.rollNumber = :rollNumber";
//
//		results = schoolReceiptDao.createQuery(sqlQuery,
//		        SchoolReceipt.class)
//		    .setParameter("superadminId", receiptRequest.getSuperadminId())
//		    .setParameter("academicSession", receiptRequest.getAcademicSession())
//		    .setParameter("grade", receiptRequest.getGrade())
//		    .setParameter("gradeSection", receiptRequest.getGradeSection())
//		    .setParameter("studentName", receiptRequest.getStudentName())
//		    .setParameter("rollNumber", receiptRequest.getRollNumber())
//		    .getResultList();		
//		return results;
//	}
	
//	@Transactional
//	public List<SchoolReceiptRequest> getReceiptDetails(SchoolReceiptRequest receiptRequest) {
//
//	    // 1️⃣ Fetch receipt headers
//	    String jpql =
//	        "SELECT SR FROM SchoolReceipt SR " +
//	        "WHERE SR.superadminId = :superadminId " +
//	        "AND SR.academicSession = :academicSession " +
//	        "AND SR.grade = :grade " +
//	        "AND SR.gradeSection = :gradeSection " +
//	        "AND SR.studentName LIKE CONCAT('%', :studentName, '%') " +
//	        "AND SR.rollNumber = :rollNumber";
//
//	    List<SchoolReceipt> receipts = schoolReceiptDao.getSession()
//	        .createQuery(jpql, SchoolReceipt.class)
//	        .setParameter("superadminId", receiptRequest.getSuperadminId())
//	        .setParameter("academicSession", receiptRequest.getAcademicSession())
//	        .setParameter("grade", receiptRequest.getGrade())
//	        .setParameter("gradeSection", receiptRequest.getGradeSection())
//	        .setParameter("studentName", receiptRequest.getStudentName())
//	        .setParameter("rollNumber", receiptRequest.getRollNumber())
//	        .getResultList();
//
//	    List<SchoolReceiptRequest> results = new ArrayList<>();
//
//	    // 2️⃣ For each receipt, fetch fee details
//	    for (SchoolReceipt r : receipts) {
//
//	        SchoolReceiptRequest dto = new SchoolReceiptRequest();
//
//	     // ===== Receipt Header =====
//	     dto.setId(r.getId());
//	     dto.setReceiptNumber(r.getReceiptNumber());
//	     dto.setAdmissionNo(r.getAdmissionNo());
//	     dto.setRollNumber(r.getRollNumber());
//	     dto.setStudentName(r.getStudentName());
//	     dto.setGrade(r.getGrade());
//	     dto.setGradeSection(r.getGradeSection());
//	     dto.setAcademicSession(r.getAcademicSession());
//	     dto.setInstallmentName(r.getInstallmentName());
//	     dto.setPaymentMode(r.getPaymentMode());
//	     dto.setPaymentDate(r.getPaymentDate());
//
//	     // ===== Amount Details =====
//	     dto.setTotalAmount(r.getTotalAmount());
//	     dto.setDiscountAmount(r.getDiscountAmount());
//	     dto.setFineAmount(r.getFineAmount());
//	     dto.setNetAmount(r.getNetAmount());
//	     dto.setStatus(r.getStatus());
//
//	     // ===== Audit Fields =====
//	     dto.setCreatedAt(r.getCreatedAt());
//	     dto.setCreatedBy(r.getCreatedBy());
//	     dto.setCreatedByName(r.getCreatedByName());
//	     dto.setSuperadminId(r.getSuperadminId());
//
//
//	        // Fetch receipt details
//	        String jpqlDetails =
//	            "SELECT RD FROM SchoolReceiptDetails RD " +
//	            "WHERE RD.superadminId = :superadminId " +
//	            "AND RD.receiptNumber = :receiptNumber";
//
//	        List<SchoolReceiptDetails> detailEntities =
//	            schoolReceiptDetailsDao.getSession()
//	                .createQuery(jpqlDetails, SchoolReceiptDetails.class)
//	                .setParameter("superadminId", receiptRequest.getSuperadminId())
//	                .setParameter("receiptNumber", r.getReceiptNumber())
//	                .getResultList();
//
//	        // Map details → DTO list
//	        List<SchoolReceiptDetailsRequest> detailDtos = new ArrayList<>();
//	        for (SchoolReceiptDetails d : detailEntities) {
//	            SchoolReceiptDetailsRequest dr = new SchoolReceiptDetailsRequest();
//	            dr.setFeeType(d.getFeeType());
//	            dr.setAmount(d.getAmount());
//	            detailDtos.add(dr);
//	        }
//
//	        dto.setReceiptDetails(detailDtos);
//	        results.add(dto);
//	    }
//
//	    return results;
//	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<SchoolReceiptRequest> getReceiptDetails(SchoolReceiptRequest receiptRequest) {

	    String sql =
	        "SELECT " +
	        " r.id, " +
	        " r.receipt_number, " +
	        " r.admission_no, " +
	        " r.roll_number, " +
	        " r.student_name, " +
	        " r.grade, " +
	        " r.grade_section, " +
	        " r.academic_session, " +
	        " r.installment_name, " +
	        " r.payment_mode, " +
	        " r.payment_date, " +
	        " r.total_amount, " +
	        " r.discount_amount, " +
	        " r.fine_amount, " +
	        " r.net_amount, " +
	        " r.status, " +
	        " r.created_at, " +
	        " r.created_by, " +
	        " r.created_by_name, " +
	        " r.superadmin_id, " +
	        " d.fee_type, " +
	        " d.amount " +
	        "FROM school_receipt r " +
	        "LEFT JOIN school_receipt_details d " +
	        "  ON r.receipt_number = d.receipt_number " +
	        "WHERE r.superadmin_id = :superadminId " +
	        "  AND r.academic_session = :academicSession " +
	        "  AND r.grade = :grade " +
	        "  AND r.grade_section = :gradeSection " +
	        "  AND r.student_name LIKE CONCAT('%', :studentName, '%') " +
	        "  AND r.roll_number = :rollNumber " +
	        "ORDER BY r.created_at DESC";

	    List<Object[]> rows = schoolReceiptDao.getSession()
	        .createNativeQuery(sql)
	        .setParameter("superadminId", receiptRequest.getSuperadminId())
	        .setParameter("academicSession", receiptRequest.getAcademicSession())
	        .setParameter("grade", receiptRequest.getGrade())
	        .setParameter("gradeSection", receiptRequest.getGradeSection())
	        .setParameter("studentName", receiptRequest.getStudentName())
	        .setParameter("rollNumber", receiptRequest.getRollNumber())
	        .getResultList();

	    // 🔹 Group by receipt number
	    Map<String, SchoolReceiptRequest> receiptMap = new LinkedHashMap<>();

	    for (Object[] row : rows) {

	        String receiptNumber = (String) row[1];

	        SchoolReceiptRequest dto = receiptMap.get(receiptNumber);
	        if (dto == null) {
	            dto = new SchoolReceiptRequest();

	            // ===== Receipt Header =====
	            dto.setId(((Number) row[0]).longValue());
	            dto.setReceiptNumber(receiptNumber);
	            dto.setAdmissionNo((String) row[2]);
	            dto.setRollNumber((String) row[3]);
	            dto.setStudentName((String) row[4]);
	            dto.setGrade((String) row[5]);
	            dto.setGradeSection((String) row[6]);
	            dto.setAcademicSession((String) row[7]);
	            dto.setInstallmentName((String) row[8]);
	            dto.setPaymentMode((String) row[9]);
	            dto.setPaymentDate((Date) row[10]);

	            // ===== Amount Details =====
	            dto.setTotalAmount((Double) row[11]);
	            dto.setDiscountAmount((Double) row[12]);
	            dto.setFineAmount((Double) row[13]);
	            dto.setNetAmount((Double) row[14]);
	            dto.setStatus((String) row[15]);

	            // ===== Audit =====
	            dto.setCreatedAt((Date) row[16]);
	            dto.setCreatedBy((String) row[17]);
	            dto.setCreatedByName((String) row[18]);
	            dto.setSuperadminId((String) row[19]);

	            dto.setReceiptDetails(new ArrayList<>());
	            receiptMap.put(receiptNumber, dto);
	        }

	        // ===== Fee Details =====
	        if (row[20] != null) {
	            SchoolReceiptDetailsRequest dr = new SchoolReceiptDetailsRequest();
	            dr.setFeeType((String) row[20]);
	            dr.setAmount((Double) row[21]);
	            dto.getReceiptDetails().add(dr);
	        }
	    }

	    return new ArrayList<>(receiptMap.values());
	}



}
