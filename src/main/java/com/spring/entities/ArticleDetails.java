package com.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "article_details")
public class ArticleDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "article_code")
	private String articleCode;
	
	@Column(name = "article_name")
	private String articleName;
	
	@Column(name = "article_sub_name")
	private String articleSubName;
	
	@Column(name = "category_Code")
	private String categoryCode;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "article_unit")
	private int articleUnit;
	
	@Column(name = "buy_rate")
	private double buyRate;
	
	@Column(name = "sell_rate")
	private double sellRate;
	
	@Column(name = "discount_percent")
	private double discountPercent;
	
	@Lob
	@Column(name = "description")
	private String description;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleSubName() {
		return articleSubName;
	}

	public void setArticleSubName(String articleSubName) {
		this.articleSubName = articleSubName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getArticleUnit() {
		return articleUnit;
	}

	public void setArticleUnit(int articleUnit) {
		this.articleUnit = articleUnit;
	}

	public double getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(double buyRate) {
		this.buyRate = buyRate;
	}

	public double getSellRate() {
		return sellRate;
	}

	public void setSellRate(double sellRate) {
		this.sellRate = sellRate;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "ArticleDetails [id=" + id + ", articleCode=" + articleCode + ", articleName=" + articleName
				+ ", articleSubName=" + articleSubName + ", categoryCode=" + categoryCode + ", status=" + status
				+ ", articleUnit=" + articleUnit + ", buyRate=" + buyRate + ", sellRate=" + sellRate
				+ ", discountPercent=" + discountPercent + ", createdAt=" + createdAt + ", createdBy=" + createdBy
				+ ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	
}
