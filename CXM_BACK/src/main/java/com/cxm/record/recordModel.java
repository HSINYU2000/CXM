package com.cxm.record;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class recordModel {
	@JsonProperty("plate")
	String sPlate;
	@JsonProperty("kmDate")
	String sKmDate;
	@JsonProperty("km")
	String sKm;
	@JsonProperty("recordDate")
	String sRecordDate;
	@JsonProperty("item")
	String sItem;
	@JsonProperty("price")
	String sPrice;
	@JsonProperty("quantity")
	String sAmount;
	@JsonProperty("salary")
	String sSalary;
	@JsonProperty("dollar")
	String sDollar;
	@JsonProperty("employee")
	String sEmployee;
	@JsonProperty("fixType")
	String sFixType;
	@JsonProperty("note")
	String sNote;
	@JsonProperty("discount")
	String sDiscount;
	@JsonProperty("payOff")
	String sPayOff;
	@JsonProperty("total")
	String sTotal;
	@JsonProperty("fileType")
	String sFileType;

	public String getPlate() {
		return sPlate;
	}

	public String getKmDate() {
		return sKmDate;
	}

	public String getKm() {
		return sKm;
	}

	public String getRecordDate() {
		return sRecordDate;
	}

	public String getItem() {
		return sItem;
	}

	public String getPrice() {
		return sPrice;
	}

	public String getAmount() {
		return sAmount;
	}

	public String getSalary() {
		return sSalary;
	}

	public String getDollar() {
		return sDollar;
	}

	public String getEmployee() {
		return sEmployee;
	}

	public String getFixType() {
		return sFixType;
	}

	public String getNote() {
		return sNote;
	}

	public String getDiscount() {
		return sDiscount;
	}

	public String getPayOff() {
		return sPayOff;
	}

	public String getTotal() {
		return sTotal;
	}

	public String getFileType() {
		return sFileType;
	}
}
