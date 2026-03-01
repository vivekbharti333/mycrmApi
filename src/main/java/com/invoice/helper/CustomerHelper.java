package com.invoice.helper;

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
import com.common.entities.UserDetails;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.common.object.request.UserRequestObject;
import com.invoice.dao.CustomersDetailsDao;
import com.invoice.entities.CustomersDetails;
import com.invoice.object.request.InvoiceRequestObject;
import com.ngo.entities.DonationDetails;

@Component
public class CustomerHelper {

	@Autowired
	private CustomersDetailsDao customersDetailsDao;

	public void validateInvoiceRequest(InvoiceRequestObject invoiceRequestObject) throws BizException {
		if (invoiceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	@Transactional
	public CustomersDetails getCustomerDetailsById(String customerName, Long companyId, String superadminId) {

		CriteriaBuilder criteriaBuilder = customersDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<CustomersDetails> criteriaQuery = criteriaBuilder.createQuery(CustomersDetails.class);
		Root<CustomersDetails> root = criteriaQuery.from(CustomersDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("customerName"), customerName);
		Predicate restriction2 = criteriaBuilder.equal(root.get("companyId"), companyId);
		Predicate restriction3 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1, restriction2, restriction3);
		CustomersDetails customersDetails = customersDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return customersDetails;
	}

	public CustomersDetails getCustomerDetailsByReqObj(InvoiceRequestObject invoiceRequest) {

		CustomersDetails customersDetails = new CustomersDetails();

		customersDetails.setCompanyId(invoiceRequest.getCompanyId());
		customersDetails.setCustomerName(invoiceRequest.getCustomerName().toUpperCase());
		customersDetails.setEmail(invoiceRequest.getCustomerEmail());
		customersDetails.setPhone(invoiceRequest.getCustomerPhone());
		customersDetails.setGstNumber(invoiceRequest.getGstNumber());
		customersDetails.setBillingAddress(invoiceRequest.getBillingAddress());
		customersDetails.setDeliveryAddresses(invoiceRequest.getDeliveryAddresses());

		customersDetails.setCreatedAt(new Date());
		customersDetails.setCreatedBy(invoiceRequest.getCreatedBy());
		customersDetails.setSuperadminId(invoiceRequest.getSuperadminId());
		return customersDetails;
	}

	@Transactional
	public CustomersDetails saveCustomerDetails(CustomersDetails customersDetails) {
		customersDetailsDao.persist(customersDetails);
		return customersDetails;
	}

	
	public List<CustomersDetails> getCustomerDetails(InvoiceRequestObject invoiceRequest) {

		String requestFor = invoiceRequest.getRequestFor();

		if ("ALL".equalsIgnoreCase(requestFor)) {
			return customersDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM CustomersDetails UD", CustomersDetails.class)
					.getResultList();
		}

		if ("BY_COMPANY_ID".equalsIgnoreCase(requestFor)) {
			return customersDetailsDao.getEntityManager().createQuery(
					"SELECT UD FROM CustomersDetails UD WHERE UD.companyId = :companyId AND UD.superadminId = :superadminId",CustomersDetails.class)
					.setParameter("companyId", invoiceRequest.getCompanyId())
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
		}

		if ("BY_SUPERADMIN".equalsIgnoreCase(requestFor)) {
			return customersDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM CustomersDetails UD WHERE UD.superadminId = :superadminId",CustomersDetails.class)
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
		}

		throw new IllegalArgumentException("Invalid requestFor value: " + requestFor);
	}
	

}
