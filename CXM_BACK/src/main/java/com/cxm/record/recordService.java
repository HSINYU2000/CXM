package com.cxm.record;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxm.database.mssql;

@Service
public class recordService {
	@Autowired
	mssql mssql;

	public String getRecord(recordModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		try {
			ay = mssql.doQuery(
					"SELECT TBLRECORD.RPlate, TBLRECORD.RDate, STRING_AGG(TBLRECORD.payOff, ',') as PAYLIST,TBLKM.KM as KM ,TBLRECORD_NOTE.FIX_TYPE as TYPE from TBLRECORD LEFT JOIN TBLKM ON TBLKM.PLATE=TBLRECORD.RPlate AND TBLKM.kmDate=TBLRECORD.RDate LEFT JOIN TBLRECORD_NOTE ON TBLRECORD_NOTE.PLATE=TBLRECORD.RPlate AND TBLRECORD_NOTE.RDATE=TBLRECORD.RDate WHERE TBLRECORD.RPlate='"
							+ req.getPlate()
							+ "' GROUP BY TBLRECORD.RDate,TBLKM.KM,TBLRECORD.RPlate,TBLRECORD_NOTE.FIX_TYPE ORDER BY TBLRECORD.RDate DESC");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("data", ay);
		return oRes.toString();
	}

	public String getKm(recordModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT  * FROM TBLKM WHERE plate='" + req.getPlate()
					+ "' GROUP BY kmDate,plate,km ORDER BY kmDate DESC");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("data", ay);
		return oRes.toString();
	}

	public String getRecordDetails(recordModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ay2 = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT  * FROM TBLRECORD WHERE RPlate='" + req.getPlate() + "' AND RDate='"
					+ req.getRecordDate() + "'");
			ay2 = mssql.doQuery("SELECT * FROM TBLRECORD_NOTE WHERE PLATE='" + req.getPlate() + "' AND RDATE='"
					+ req.getRecordDate() + "'");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("data", ay);
		oRes.put("data2", ay2);
		return oRes.toString();
	}

	public String delKm(recordModel req) {
		JSONObject oRes = new JSONObject();
		int i = 0;
		try {
			i = mssql.doUpdate("DELETE FROM TBLKM WHERE plate='" + req.getPlate() + "' AND kmDate='" + req.getKmDate()
					+ "' AND km='" + req.getKm() + "'");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("data", i);
		return oRes.toString();
	}

	public String addKm(recordModel req) {
		JSONObject oRes = new JSONObject();
		int i = 0;
		try {
			i = mssql.doUpdate("INSERT INTO TBLKM VALUES('" + req.getKmDate() + "','" + req.getKm() + "','"
					+ req.getPlate() + "')");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("data", i);
		return oRes.toString();
	}

	public String addRecord(recordModel req) {
		JSONObject oRes = new JSONObject();
		int i = 0;
		try {
			i = mssql.doUpdate("INSERT INTO TBLRECORD VALUES('" + req.getPlate() + "','" + req.getRecordDate() + "','"
					+ req.getItem() + "','" + req.getPrice() + "','" + req.getAmount() + "','0','" + req.getSalary()
					+ "','" + req.getDollar() + "','','否')");
		} catch (Exception e) {
			System.out.println(e);
		}
		return oRes.toString();
	}

	public String delRecord(recordModel req) {
		JSONObject oRes = new JSONObject();
		int i = 0;
		try {
			i = mssql.doUpdate("DELETE FROM TBLRECORD WHERE RPlate='" + req.getPlate() + "' AND RDate='"
					+ req.getRecordDate() + "' AND Item='" + req.getItem() + "' AND Price='" + req.getPrice()
					+ "' AND Quantity='" + req.getAmount() + "' AND Salary='" + req.getSalary() + "' AND Dollar='"
					+ req.getDollar() + "'");
		} catch (Exception e) {
			System.out.println(e);
		}
		return oRes.toString();
	}

	public String saveNote(recordModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		int i = 0;
		try {
			ay = mssql.doQuery("SELECT * FROM TBLRECORD_NOTE WHERE PLATE='" + req.getPlate() + "' AND RDATE='"
					+ req.getRecordDate() + "'");
			if (ay.length() > 0) {
				i = mssql.doUpdate(
						"UPDATE TBLRECORD_NOTE SET EMPLOYEE='" + req.getEmployee() + "',FIX_TYPE='" + req.getFixType()
								+ "', NOTE='" + req.getNote() + "', DISCOUNT='" + req.getDiscount() + "', PAYOFF='"
								+ req.getPayOff() + "', TOTAL='" + req.getTotal() + "', FILE_TYPE='" + req.getFileType()
								+ "' WHERE PLATE='" + req.getPlate() + "' AND RDATE='" + req.getRecordDate() + "'");
			} else {
				i = mssql.doUpdate("INSERT INTO TBLRECORD_NOTE VALUES('" + req.getPlate() + "','" + req.getRecordDate()
						+ "','" + req.getEmployee() + "','" + req.getFixType() + "','" + req.getNote() + "','"
						+ req.getDiscount() + "','" + req.getPayOff() + "','" + req.getTotal() + "','"
						+ req.getFileType() + "')");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return oRes.toString();
	}

	public String getNotPay(recordModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		int notPay = 0;

		List<String> ayPlate = Arrays.asList(req.getPlate().split(","));
		try {
			for (int i = 0; i < ayPlate.size(); i++) {
				ay = mssql.doQuery("SELECT * FROM TBLRECORD_NOTE WHERE PLATE='" + ayPlate.get(i) + "'");
				if (ay.length() > 0) {
					for (int x = 0; x < ay.length(); x++) {

						String payoff = ay.getJSONObject(x).optString("PAYOFF").toString() == null
								|| ay.getJSONObject(x).optString("PAYOFF").toString().equals("") ? "0"
										: ay.getJSONObject(x).optString("PAYOFF").toString();
						String total = ay.getJSONObject(x).optString("TOTAL").toString() == null
								|| ay.getJSONObject(x).optString("TOTAL").toString().equals("") ? "0"
										: ay.getJSONObject(x).optString("TOTAL").toString();
						notPay = notPay + Integer.valueOf(total) - Integer.valueOf(payoff);
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("result", notPay);
		return oRes.toString();
	}

	public String getRecFixType() {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ayFixType = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT DISTINCT FIX_TYPE FROM TBLRECORD_NOTE WHERE FIX_TYPE!=''");
			for (int i = 0; i < ay.length(); i++) {
				JSONObject obj = new JSONObject();

				obj.put("label", ay.getJSONObject(i).optString("FIX_TYPE"));
				obj.put("value", ay.getJSONObject(i).optString("FIX_TYPE"));
				ayFixType.put(obj);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("fixTypeOpt", ayFixType);
		return oRes.toString();
	}
}
