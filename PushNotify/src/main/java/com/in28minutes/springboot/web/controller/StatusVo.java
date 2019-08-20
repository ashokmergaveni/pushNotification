package com.in28minutes.springboot.web.controller;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


public class StatusVo {
	
public String empUserid;
	
	public String getEmpUserid() {
	return empUserid;
}
public void setEmpUserid(String empUserid) {
	this.empUserid = empUserid;
}
	//@NotNull(message="checkinstatus not allowed null")
	public String checkinStatus; 
public String getCheckinStatus() {
	return checkinStatus;
}
public void setCheckinStatus(String checkinStatus) {
	this.checkinStatus = checkinStatus;
}

	
}
