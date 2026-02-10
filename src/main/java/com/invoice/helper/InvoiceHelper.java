package com.invoice.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.invoice.dao.InvoiceDetailsDao;
import com.invoice.dao.InvoiceNumberDao;
import com.invoice.entities.InvoiceDetails;
import com.invoice.entities.InvoiceNumber;
import com.invoice.object.request.InvoiceItemRequest;
import com.invoice.object.request.InvoiceRequestObject;

@Component
public class InvoiceHelper {

	@Autowired
	private InvoiceNumberDao invoiceNumberDao;

	@Autowired
	private InvoiceDetailsDao invoiceDetailsDao;
	
	public void validateInvoiceRequest(InvoiceRequestObject invoiceRequestObject) throws BizException {
		if (invoiceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	public InvoiceNumber getInvoiceNumberByReqObj(InvoiceRequestObject invoiceRequest) {

		InvoiceNumber invoiceNumber = new InvoiceNumber();

		invoiceNumber.setCompanyId(invoiceRequest.getCompanyId());
		
		invoiceNumber.setCompanyLogo(invoiceRequest.getCompanyLogo());
		invoiceNumber.setCompanyName(invoiceRequest.getCompanyName());
		invoiceNumber.setOfficeAddress(invoiceRequest.getOfficeAddress());
		invoiceNumber.setRegAddress(invoiceRequest.getRegAddress());
		invoiceNumber.setMobileNo(invoiceRequest.getMobileNo());
		invoiceNumber.setEmailId(invoiceRequest.getEmailId());
		invoiceNumber.setWebsite(invoiceRequest.getWebsite());
		invoiceNumber.setGstNumber(invoiceRequest.getGstNumber());
		invoiceNumber.setPanNumber(invoiceRequest.getPanNumber());
		
		invoiceNumber.setCustomerName(invoiceRequest.getCustomerName());
		invoiceNumber.setCustomerEmail(invoiceRequest.getCustomerEmail());
		invoiceNumber.setCustomerPhone(invoiceRequest.getCustomerPhone());
		invoiceNumber.setCustomerGstNumber(invoiceRequest.getCustomerGstNumber());
		invoiceNumber.setBillingAddress(invoiceRequest.getBillingAddress());
		invoiceNumber.setDeliveryAddresses(invoiceRequest.getDeliveryAddresses());
		
		invoiceNumber.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
		invoiceNumber.setDueDate(invoiceRequest.getDueDate());
		invoiceNumber.setSubtotal(invoiceRequest.getSubtotal());
		invoiceNumber.setDiscount(invoiceRequest.getDiscount());
		
		invoiceNumber.setCgstRate(invoiceRequest.getCgstRate());
		invoiceNumber.setCgstAmount(invoiceRequest.getCgstAmount());
		invoiceNumber.setSgstRate(invoiceRequest.getSgstRate());
		invoiceNumber.setSgstAmount(invoiceRequest.getSgstAmount());
		invoiceNumber.setIgstRate(invoiceRequest.getIgstRate());
		invoiceNumber.setIgstAmount(invoiceRequest.getIgstAmount());
		
		invoiceNumber.setTotalAmount(invoiceRequest.getTotalAmount());
		invoiceNumber.setStatus(Status.ACTIVE.name());
		invoiceNumber.setPaymentMode(invoiceRequest.getPaymentMode());
		invoiceNumber.setTransactionId(invoiceRequest.getTransactionId());
		invoiceNumber.setPaymentStatus(invoiceRequest.getPaymentStatus());
		invoiceNumber.setInvoiceStatus(invoiceRequest.getInvoiceStatus());
		invoiceNumber.setCreatedAt(new Date());
		invoiceNumber.setCreatedBy(invoiceRequest.getCreatedBy());
		invoiceNumber.setSuperadminId(invoiceRequest.getSuperadminId());

		return invoiceNumber;
	}

	@Transactional
	public InvoiceNumber saveInvoiceNumber(InvoiceNumber invoiceNumber) {
		invoiceNumberDao.persist(invoiceNumber);
		return invoiceNumber;
	}


//	public InvoiceDetails getInvoiceDetailsByReqObj(InvoiceRequestObject invoiceRequest) {
//		
//		for(InvoiceItemRequest invoiceItemRequest : invoiceRequest.getItems()) {
//			
//			InvoiceDetails invoiceDetails = new InvoiceDetails();
//			
//			invoiceDetails.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
//			invoiceDetails.setProductName(invoiceItemRequest.getProductName());
//			invoiceDetails.setDescription(invoiceItemRequest.getDescription());
//			invoiceDetails.setRate(invoiceItemRequest.getRate());
//			invoiceDetails.setQuantity(invoiceItemRequest.getQuantity());
//			invoiceDetails.setAmount(invoiceItemRequest.getAmount());
//			invoiceDetails.setStatus(Status.ACTIVE.name());
//			invoiceDetails.setCreatedAt(LocalDate.now());
//			invoiceDetails.setSuperadminId(invoiceRequest.getSuperadminId());
//			
//			this.saveInvoiceDetails(invoiceDetails);
//		}
//		
//		return null;
//	}
	

	public List<InvoiceDetails> getInvoiceDetailsByReqObj(InvoiceRequestObject req) {

	    return req.getItems().stream().map(item -> {
	        InvoiceDetails d = new InvoiceDetails();
	        d.setInvoiceNumber(req.getInvoiceNumber());
	        d.setProductName(item.getProductName());
	        d.setDescription(item.getDescription());
	        d.setRate(item.getRate());
	        d.setQuantity(item.getQuantity());
	        d.setAmount(item.getAmount());
	        d.setStatus(Status.ACTIVE.name());
	        d.setCreatedAt(new Date());
	        d.setSuperadminId(req.getSuperadminId());
	        return d;
	    }).collect(Collectors.toList());
	}


	@Transactional
	public void saveInvoiceDetails(List<InvoiceDetails> details) {
	    details.forEach(invoiceDetailsDao::persist);
	}

	
//	@Transactional
//	public InvoiceDetails saveInvoiceDetails(InvoiceDetails invoiceDetails) {
//		invoiceDetailsDao.persist(invoiceDetails);
//		return invoiceDetails;
//	}
	
	

//	@SuppressWarnings("unchecked")
//	public List<InvoiceRequestObject> getInvoiceNumberList(InvoiceRequestObject invoiceRequest) {
//			List<InvoiceRequestObject> results = invoiceNumberDao.getEntityManager()
//					.createQuery("SELECT n, d FROM InvoiceNumber n JOIN InvoiceDetails d ON n.invoiceNumber = d.invoiceNumber WHERE n.invoiceNumber = :invoiceNumber")
//					.setParameter("superadminId", invoiceRequest.getSuperadminId())
//					.getResultList();
//			return results; 	
//	}	
	


//	@SuppressWarnings("unchecked")
//	public List<InvoiceDetails> getInvoiceDetailsList(InvoiceRequestObject invoiceRequest) {
//		if (invoiceRequest.getRequestFor().equals("SUPALL")) {
//			List<InvoiceDetails> results = invoiceDetailsDao.getEntityManager()
//					.createQuery("SELECT UD FROM InvoiceDetails UD WHERE UD.invoiceNo =:invoiceNo AND UD.superadminId =:superadminId")
//					.setParameter("invoiceNo", invoiceRequest.getInvoiceNumber())
//					.setParameter("superadminId", invoiceRequest.getSuperadminId())
//					.getResultList();
//			return results;
//		} else if (invoiceRequest.getRequestFor().equals("LOGINID")) {
//			List<InvoiceDetails> results = invoiceDetailsDao.getEntityManager()
//					.createQuery("SELECT UD FROM InvoiceDetails UD WHERE UD.invoiceNo =:invoiceNo AND UD.createdBy =:createdBy AND UD.superadminId =:superadminId ODER BY UD.id")
//					.setParameter("invoiceNo", invoiceRequest.getInvoiceNumber())
//					.setParameter("createdBy", invoiceRequest.getCreatedBy())
//					.setParameter("superadminId", invoiceRequest.getSuperadminId())
//					.getResultList();
//			return results;
//		}
//		return null;
//
//	}

	@SuppressWarnings("unchecked")
	public List<InvoiceRequestObject> getInvoiceDetailsNumber(InvoiceRequestObject invoiceRequest) {
			List<InvoiceRequestObject> results = invoiceNumberDao.getEntityManager()
					.createQuery("SELECT UD FROM InvoiceNumber UD WHERE UD.invoiceNumber =:invoiceNumber AND UD.superadminId =:superadminId")
					.setParameter("invoiceNumber", invoiceRequest.getInvoiceNumber())
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
			return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<InvoiceItemRequest> getInvoiceDetails(InvoiceRequestObject invoiceRequest) {
			List<InvoiceItemRequest> results = invoiceDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM InvoiceDetails UD WHERE UD.invoiceNumber =:invoiceNumber AND UD.superadminId =:superadminId")
					.setParameter("invoiceNumber", invoiceRequest.getInvoiceNumber())
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
			return results;
	}
	
	@SuppressWarnings("unchecked")
//	public List<InvoiceRequestObject> getInvoiceWithDetails(InvoiceRequestObject invoiceRequest) {
//	public InvoiceRequestObject getInvoiceWithDetails(InvoiceRequestObject invoiceRequest) {
//
//	    String jpql;
//
//	    boolean fetchAll =
//	            invoiceRequest.getRequestFor() == null ||
//	            invoiceRequest.getRequestFor().equalsIgnoreCase("ALL");
//
//	    if (fetchAll) {
//	        jpql =
//	            "SELECT n, d FROM InvoiceNumber n LEFT JOIN InvoiceDetails d ON n.invoiceNumber = d.invoiceNumber AND d.superadminId = :superadminId "
//	            + "WHERE n.superadminId = :superadminId ORDER BY n.invoiceNumber";
//	    } else {
//	        jpql =
//	            "SELECT n, d FROM InvoiceNumber n LEFT JOIN InvoiceDetails d ON n.invoiceNumber = d.invoiceNumber AND d.superadminId = :superadminId "
//	            + "WHERE n.superadminId = :superadminId AND n.invoiceNumber = :invoiceNumber ORDER BY n.invoiceNumber";
//	    }
//
//	    var query = invoiceNumberDao.getEntityManager().createQuery(jpql)
//	            .setParameter("superadminId", invoiceRequest.getSuperadminId());
//
//	    if (!fetchAll) {
//	        query.setParameter("invoiceNumber", invoiceRequest.getInvoiceNumber());
//	    }
//
//	    List<Object[]> rows = query.getResultList();
//
//	    if (rows == null || rows.isEmpty()) {
//	        return new Array<>();
//	    }
//
//	    Map<String, InvoiceRequestObject> invoiceMap = new LinkedHashMap<>();
//
//	    for (Object[] row : rows) {
//
//	        InvoiceNumber header = (InvoiceNumber) row[0];
//	        InvoiceDetails detail = (InvoiceDetails) row[1];
//
//	        InvoiceRequestObject invoice =
//	                invoiceMap.get(header.getInvoiceNumber());
//
//	        if (invoice == null) {
//	            invoice = new InvoiceRequestObject();
//	            invoice.setCompanyId(header.getCompanyId());
//	            
//	            invoice.setInvoiceNumber(header.getInvoiceNumber());
//	            invoice.setInvoiceDate(header.getCreatedAt());
//	            invoice.setDueDate(header.getDueDate());
//	            invoice.setSubtotal(header.getSubtotal());
//	            invoice.setDiscount(header.getDiscount());
//	            invoice.setTotalAmount(header.getTotalAmount());
//	            invoice.setStatus(header.getStatus());
//	            invoice.setPaymentMode(header.getPaymentMode());
//	            invoice.setTransactionId(header.getTransactionId());
//	            invoice.setPaymentStatus(header.getPaymentStatus());
//	            invoice.setInvoiceStatus(header.getInvoiceStatus());
//	            invoice.setCreatedAt(header.getCreatedAt());
//	            invoice.setSuperadminId(header.getSuperadminId());
//	            invoice.setCreatedBy(header.getCreatedBy());
//	            
//	         // ✅ Add customer details
//	            invoice.setCustomerName(header.getCustomerName());
//	            invoice.setEmail(header.getEmail());
//	            invoice.setPhone(header.getPhone());
//	            invoice.setGstNumber(header.getGstNumber());
//	            invoice.setBillingAddress(header.getBillingAddress());
//	            invoice.setDeliveryAddresses(header.getDeliveryAddresses());
//	            
//	         // ✅ GST details
//	            invoice.setCgstRate(header.getCgstRate());
//	            invoice.setCgstAmount(header.getCgstAmount());
//
//	            invoice.setSgstRate(header.getSgstRate());
//	            invoice.setSgstAmount(header.getSgstAmount());
//
//	            invoice.setIgstRate(header.getIgstRate());
//	            invoice.setIgstAmount(header.getIgstAmount());
//
//	            
//	            invoice.setItems(new ArrayList<>());
//	            invoice.setRespCode(200);
//	            invoice.setRespMesg("Invoice fetched successfully");
//
//	            invoiceMap.put(header.getInvoiceNumber(), invoice);
//	        }
//
//	        // add item if present
//	        if (detail != null) {
//	            InvoiceItemRequest item = new InvoiceItemRequest();
//	            item.setProductName(detail.getProductName());
//	            item.setDescription(detail.getDescription());
//	            item.setRate(detail.getRate());
//	            item.setQuantity(detail.getQuantity());
//	            item.setAmount(detail.getAmount());
//	            item.setStatus(detail.getStatus());
//
//	            invoice.getItems().add(item);
//	        }
//	    }
//
//	    return new Array<>(invoiceMap.values());
//	}

	

	public InvoiceRequestObject getInvoiceWithDetails(InvoiceRequestObject invoiceRequest) {

	    String jpql;

	    boolean fetchAll =
	            invoiceRequest.getRequestFor() == null ||
	            invoiceRequest.getRequestFor().equalsIgnoreCase("ALL");

	    if (fetchAll) {
	        jpql =
	            "SELECT n, d FROM InvoiceNumber n " +
	            "LEFT JOIN InvoiceDetails d ON n.invoiceNumber = d.invoiceNumber " +
	            "AND d.superadminId = :superadminId " +
	            "WHERE n.superadminId = :superadminId " +
	            "ORDER BY n.invoiceNumber";
	    } else {
	        jpql =
	            "SELECT n, d FROM InvoiceNumber n " +
	            "LEFT JOIN InvoiceDetails d ON n.invoiceNumber = d.invoiceNumber " +
	            "AND d.superadminId = :superadminId " +
	            "WHERE n.superadminId = :superadminId " +
	            "AND n.invoiceNumber = :invoiceNumber " +
	            "ORDER BY n.invoiceNumber";
	    }

	    var query = invoiceNumberDao.getEntityManager()
	            .createQuery(jpql, Object[].class)
	            .setParameter("superadminId", invoiceRequest.getSuperadminId());

	    if (!fetchAll) {
	        query.setParameter("invoiceNumber", invoiceRequest.getInvoiceNumber());
	    }

	    List<Object[]> rows = query.getResultList();

	    if (rows == null || rows.isEmpty()) {
	        return null;
	    }

	    Map<String, InvoiceRequestObject> invoiceMap = new LinkedHashMap<>();

	    for (Object[] row : rows) {

	        InvoiceNumber header = (InvoiceNumber) row[0];
	        InvoiceDetails detail = (InvoiceDetails) row[1];

	        InvoiceRequestObject invoice =
	                invoiceMap.get(header.getInvoiceNumber());

	        if (invoice == null) {
	            invoice = new InvoiceRequestObject();

	            // Header
	            invoice.setCompanyId(header.getCompanyId());
	            invoice.setInvoiceNumber(header.getInvoiceNumber());
	            invoice.setInvoiceDate(header.getCreatedAt());
	            invoice.setDueDate(header.getDueDate());
	            invoice.setSubtotal(header.getSubtotal());
	            invoice.setDiscount(header.getDiscount());
	            invoice.setTotalAmount(header.getTotalAmount());
	            invoice.setStatus(header.getStatus());
	            invoice.setPaymentMode(header.getPaymentMode());
	            invoice.setTransactionId(header.getTransactionId());
	            invoice.setPaymentStatus(header.getPaymentStatus());
	            invoice.setInvoiceStatus(header.getInvoiceStatus());
	            invoice.setCreatedAt(header.getCreatedAt());
	            invoice.setSuperadminId(header.getSuperadminId());
	            invoice.setCreatedBy(header.getCreatedBy());

	            // Customer
	            invoice.setCustomerName(header.getCustomerName());
	            invoice.setCustomerEmail(invoiceRequest.getCustomerEmail());
	    		invoice.setCustomerPhone(invoiceRequest.getCustomerPhone());
	    		invoice.setCustomerGstNumber(invoiceRequest.getCustomerGstNumber());
	            invoice.setBillingAddress(header.getBillingAddress());
	            invoice.setDeliveryAddresses(header.getDeliveryAddresses());

	            // GST
	            invoice.setCgstRate(header.getCgstRate());
	            invoice.setCgstAmount(header.getCgstAmount());
	            invoice.setSgstRate(header.getSgstRate());
	            invoice.setSgstAmount(header.getSgstAmount());
	            invoice.setIgstRate(header.getIgstRate());
	            invoice.setIgstAmount(header.getIgstAmount());

	            invoice.setItems(new ArrayList<>());
	            invoice.setRespCode(200);
	            invoice.setRespMesg("Invoice fetched successfully");

	            invoiceMap.put(header.getInvoiceNumber(), invoice);
	        }

	        // Items
	        if (detail != null) {
	            InvoiceItemRequest item = new InvoiceItemRequest();
	            item.setProductName(detail.getProductName());
	            item.setDescription(detail.getDescription());
	            item.setRate(detail.getRate());
	            item.setQuantity(detail.getQuantity());
	            item.setAmount(detail.getAmount());
	            item.setStatus(detail.getStatus());

	            invoice.getItems().add(item);
	        }
	    }

	    // ✅ Return only ONE invoice
	    return invoiceMap.values().iterator().next();
	}





}
