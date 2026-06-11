package com.freelance.app;

import java.util.*;
import java.math.BigDecimal;
import com.freelance.bean.Project;
import com.freelance.bean.User;
import com.freelance.service.FreelanceService;
import com.freelance.util.ValidationException;
public class FreelanceMain {
	private static FreelanceService service = new 
			FreelanceService(); 
			public static void main(String[] args) { 
			java.util.Scanner sc = new java.util.Scanner(System.in); 
			System.out.println("--- Freelance Project Posting & Bidding Console ---"); 
			// DEMO 1: Register a client user 
			try { 
			User client = new User(); 
			client.setUserID("UCL3001"); 
			client.setFullName("Meenakshi Rao"); 
			client.setEmail("meenakshi.rao@startup.com"); 
			client.setMobile("9998887771"); 
			client.setRole("CLIENT"); 
			client.setPrimarySkillOrCompany("BrightIdeas Labs"); 
			client.setStatus("ACTIVE"); 
			boolean ok = service.registerNewUser(client); 
			System.out.println(ok ? "CLIENT REGISTERED" : "CLIENT REGISTRATION FAILED"); 
			} catch (ValidationException e) { 
			System.out.println("Validation Error: " + e.toString()); 
			} catch (Exception e) { 
			System.out.println("System Error: " + e.getMessage()); 
			} 
			// DEMO 2: Register a freelancer user 
			try { 
			User freelancer = new User(); 
			freelancer.setUserID("UFR3001"); 
			freelancer.setFullName("Ravi Shankar"); 
			freelancer.setEmail("ravi.shankar.dev@example.com"); 
			freelancer.setMobile("9991112223"); 
			freelancer.setRole("FREELANCER"); 
			freelancer.setPrimarySkillOrCompany("Java & Spring Boot Developer"); 
			freelancer.setStatus("ACTIVE"); 
			boolean ok = service.registerNewUser(freelancer); 
			System.out.println(ok ? "FREELANCER REGISTERED" : "FREELANCER REGISTRATION FAILED"); 
			} catch (ValidationException e) { 
			System.out.println("Validation Error: " + e.toString()); 
			} catch (Exception e) { 
			System.out.println("System Error: " + e.getMessage()); 
			} 
			// DEMO 3: Client posts a new project 
			try { 
			Project p = new Project(); 
			p.setClientUserID("UCL3001"); 
			p.setProjectTitle("Build Expense Tracking REST API"); 
			p.setProjectDescription("Design and implement RESTful services for an expense tracking module using Java and JDBC."); 
			p.setBudgetMin(new java.math.BigDecimal("15000.00")); 
			p.setBudgetMax(new java.math.BigDecimal("25000.00")); 
			p.setStatus("OPEN"); 
			boolean ok = service.postNewProject(p); 
			System.out.println(ok ? "PROJECT POSTED" : "PROJECT POSTING FAILED"); 
			} catch (ValidationException e) { 
			System.out.println("Validation Error: " + e.toString()); 
			} catch (Exception e) { 
			System.out.println("System Error: " + e.getMessage()); 
			} 
			sc.close(); 
			}

}
