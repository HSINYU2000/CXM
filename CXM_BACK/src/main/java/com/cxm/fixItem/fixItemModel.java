package com.cxm.fixItem;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class fixItemModel {
	@JsonProperty("item_type")
	String sType;
	@JsonProperty("item")
	String sItem;
	@JsonProperty("item_price")
	String sPrice;
	@JsonProperty("item_cost")
	String sCost;
	@JsonProperty("item_stock")
	String sStock;
	@JsonProperty("status")
	String sStatus;
	@JsonProperty("id")
	String sID;

	public String getType() {
		return sType;
	}

	public String getItem() {
		return sItem;
	}

	public String getPrice() {
		return sPrice;
	}

	public String getCost() {
		return sCost;
	}

	public String getStock() {
		return sStock;
	}

	public String getStatus() {
		return sStatus;
	}

	public String getID() {
		return sID;
	}
}
