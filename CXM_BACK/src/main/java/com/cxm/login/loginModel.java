package com.cxm.login;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class loginModel {
	@JsonProperty("acc")
	String sAcc;
	@JsonProperty("pazz")
	String sPazz;

	public String getAcc() {
		return sAcc;
	}

	public String getPazz() {
		return sPazz;
	}
}
