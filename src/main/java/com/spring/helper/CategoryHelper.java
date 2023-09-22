package com.spring.helper;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.CategoryDetailsDao;
import com.spring.entities.CategoryDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ArticleRequestObject;


@Component
public class CategoryHelper {
	
	@Autowired
	private CategoryDetailsDao categoryDetailsDao;
	
	public void validateArticleRequest(ArticleRequestObject articleRequestObject) 
			throws BizException
	{ 
		if(articleRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null"); 
		}
	}
	
	
	@Transactional
	public CategoryDetails getcategoryDetailsByNameAndCode(ArticleRequestObject articleRequest) {
		
	    CriteriaBuilder criteriaBuilder = categoryDetailsDao.getSession().getCriteriaBuilder();
	    CriteriaQuery<CategoryDetails> criteriaQuery = criteriaBuilder.createQuery(CategoryDetails.class);
	    Root<CategoryDetails> root = criteriaQuery.from(CategoryDetails.class);
	    Predicate restriction = criteriaBuilder.equal(root.get("categoryName"), articleRequest.getCategoryName());
	    restriction = criteriaBuilder.equal(root.get("categoryCode"), articleRequest.getCategoryCode());
	    criteriaQuery.where(restriction);
	    CategoryDetails categoryDetails = categoryDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
	    return categoryDetails;
	}


	public CategoryDetails getCategoryDetailsByReqObj(ArticleRequestObject categoryRequest) {
		
		CategoryDetails categoryDetails = new CategoryDetails();
		
		categoryDetails.setCategoryCode(categoryRequest.getCategoryCode());
		categoryDetails.setCategoryName(categoryRequest.getCategoryName());
		categoryDetails.setHsnCode(categoryDetails.getHsnCode());
		categoryDetails.setCreatedBy(categoryRequest.getCreatedBy());
		categoryDetails.setCreatedAt(new Date());
		return categoryDetails;
	}
	
	@Transactional
	public CategoryDetails saveCategoryDetails(CategoryDetails categoryDetails) { 
		categoryDetailsDao.persist(categoryDetails);
		return categoryDetails;
	}


	@SuppressWarnings("unchecked")
	public List<CategoryDetails> getCategoryList(ArticleRequestObject leadRequest) {
		List<CategoryDetails> results = categoryDetailsDao.getEntityManager()
		.createQuery("SELECT CD FROM CategoryDetails CD")
		.getResultList();
		return results;
	}

}
