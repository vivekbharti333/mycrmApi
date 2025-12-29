package com.school.helper;

import java.util.Date;

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

}
