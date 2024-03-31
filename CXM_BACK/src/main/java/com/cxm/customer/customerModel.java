package com.cxm.customer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class customerModel {
	@JsonProperty("name")
	String sName;
	@JsonProperty("birth")
	String sBirth;
	@JsonProperty("phone")
	String sPhone;
	@JsonProperty("note")
	String sNote;
	@JsonProperty("moto")
	List<Map<String, Object>> ayMoto;

	public String getName() {
		return sName;
	}

	public String getBirth() {
		return sBirth;
	}

	public String getPhone() {
		return sPhone;
	}

	public String getNote() {
		return sNote;
	}

	public List<Map<String, Object>> getMoto() {
		return ayMoto;
	}
}
