package com.freelance.bean;

public class User {
	private String userID;
	private String fullName;
	private String email;
	private String mobile;
	private String role;
	private String primarySkillOrCompany ;
	private String status;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPrimarySkillOrCompany() {
		return primarySkillOrCompany;
	}
	public void setPrimarySkillOrCompany(String primarySkillOrCompany) {
		this.primarySkillOrCompany = primarySkillOrCompany;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
