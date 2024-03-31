package com.cxm.employee;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class employeeModel {
	@JsonProperty("name")
	String sName;
	@JsonProperty("acc")
	String sAcc;
	@JsonProperty("birth")
	String sBirth;
	@JsonProperty("phone")
	String sPhone;
	@JsonProperty("date")
	String sDate;
	@JsonProperty("role")
	String sRole;
	@JsonProperty("pass")
	String sPass;

	public String getName() {
		return sName;
	}

	public String getAcc() {
		return sAcc;
	}

	public String getBirth() {
		return sBirth;
	}

	public String getPhone() {
		return sPhone;
	}

	public String getDate() {
		return sDate;
	}

	public String getRole() {
		return sRole;
	}

	public String getPass() {
		return sPass;
	}
}
