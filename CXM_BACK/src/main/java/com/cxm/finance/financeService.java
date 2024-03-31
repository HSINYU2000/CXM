package com.cxm.finance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxm.database.mssql;

@Service
public class financeService {
	@Autowired
	mssql mssql;

	/*
	 * 單日營業額
	 */
	public String getDaySum() {
		JSONObject oRes = new JSONObject();
		JSONArray ayDate = new JSONArray();
		try {
			ayDate = mssql.doQuery("SELECT DISTINCT RDate FROM TBLRECORD");
			if (ayDate.length() > 0) {
				for (int i = 0; i < ayDate.length(); i++) {
					JSONObject oDate = ayDate.getJSONObject(i);
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("RDate", ayDate);
		return oRes.toString();
	}
}
