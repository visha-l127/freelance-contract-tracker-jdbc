package com.freelance.bean;
import java.math.BigDecimal;
import java.sql.Date;

public class Project {
	private int projectID;
    private String clientUserID;
    private String projectTitle;
    private String projectDescription;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private Date postedDate;
    private String status;
    private Integer awardedBidID;
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getClientUserID() {
		return clientUserID;
	}
	public void setClientUserID(String clientUserID) {
		this.clientUserID = clientUserID;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public BigDecimal getBudgetMin() {
		return budgetMin;
	}
	public void setBudgetMin(BigDecimal budgetMin) {
		this.budgetMin = budgetMin;
	}
	public BigDecimal getBudgetMax() {
		return budgetMax;
	}
	public void setBudgetMax(BigDecimal budgetMax) {
		this.budgetMax = budgetMax;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAwardedBidID() {
		return awardedBidID;
	}
	public void setAwardedBidID(Integer awardedBidID) {
		this.awardedBidID = awardedBidID;
	}
    
}
