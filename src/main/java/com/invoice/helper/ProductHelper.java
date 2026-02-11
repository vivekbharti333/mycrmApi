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
import com.invoice.dao.ProductDetailsDao;
import com.invoice.entities.CustomersDetails;
import com.invoice.entities.ProductDetails;
import com.invoice.object.request.InvoiceRequestObject;
import com.invoice.object.request.ProductRequestObject;
import com.ngo.entities.DonationDetails;

@Component
public class ProductHelper {

	@Autowired
	private ProductDetailsDao productDetailsDao;

	public void validateProductRequest(ProductRequestObject productRequestObject) throws BizException {
		if (productRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	@Transactional
	public ProductDetails getProductDetailsByName(String productName, String superadminId) {

		CriteriaBuilder criteriaBuilder = productDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<ProductDetails> criteriaQuery = criteriaBuilder.createQuery(ProductDetails.class);
		Root<ProductDetails> root = criteriaQuery.from(ProductDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("productName"), productName);
		Predicate restriction2 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1, restriction2);
		ProductDetails productDetails = productDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return productDetails;
	}

	public ProductDetails getProductDetailsByReqObj(ProductRequestObject productRequest) {

		ProductDetails productDetails = new ProductDetails();
		
		productDetails.setProductName(productRequest.getProductName());
		productDetails.setDescription(productRequest.getDescription());
		productDetails.setRate(productRequest.getRate());
		productDetails.setQuantityType(productRequest.getQuantityType());
		productDetails.setQuantity(productRequest.getQuantity());
		
		productDetails.setCreatedAt(new Date());
		productDetails.setCreatedBy(productRequest.getCreatedBy());
		productDetails.setSuperadminId(productRequest.getSuperadminId());
		return productDetails;
	}

	@Transactional
	public ProductDetails saveProductDetails(ProductDetails productDetails) {
		productDetailsDao.persist(productDetails);
		return productDetails;
	}

	
	public List<ProductDetails> getProductDetails(ProductRequestObject productRequest) {

		String requestFor = productRequest.getRequestFor();

		if ("ALL".equalsIgnoreCase(requestFor)) {
			return productDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM ProductDetails UD", ProductDetails.class)
					.getResultList();
		}

		if ("BY_COMPANY_ID".equalsIgnoreCase(requestFor)) {
			return productDetailsDao.getEntityManager().createQuery(
					"SELECT UD FROM ProductDetails UD WHERE UD.companyId = :companyId AND UD.superadminId = :superadminId",ProductDetails.class)
					.setParameter("companyId", productRequest.getCompanyId())
					.setParameter("superadminId", productRequest.getSuperadminId())
					.getResultList();
		}

		if ("BY_SUPERADMIN".equalsIgnoreCase(requestFor)) {
			return productDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM ProductDetails UD WHERE UD.superadminId = :superadminId",ProductDetails.class)
					.setParameter("superadminId", productRequest.getSuperadminId())
					.getResultList();
		}

		throw new IllegalArgumentException("Invalid requestFor value: " + requestFor);
	}
	

}
