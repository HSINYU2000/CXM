package com.cxm.customer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxm.database.mssql;

@Service
public class customerService {

	@Autowired
	mssql mssql;

	public String getCustomer() {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ayCusData = new JSONArray();
		try {
//			ay = mssql.doQuery("SELECT * FROM TBLCUSTOMER CUS LEFT JOIN TBLMOTOCYCLE MOTO ON CUS.Plate=MOTO.Plate");
			ay = mssql.doQuery(
					"SELECT DISTINCT  Phone,(SELECT Plate+';' from TBLCUSTOMER AS t1 WHERE t1.Phone=t2.Phone AND t1.Name=t2.Name for xml path('')) as allPlate,Name,(SELECT First+';' from TBLCUSTOMER AS t3 WHERE t3.Phone=t2.Phone AND t3.Name=t2.Name  for xml path(''))as allFirst,(SELECT Birth+';' from TBLCUSTOMER AS t4 WHERE t4.Phone=t2.Phone AND t4.Name=t2.Name  for xml path(''))as allBirth      FROM TBLCUSTOMER AS t2 ");
			if (ay.length() > 0) {
				for (int i = 0; i < ay.length(); i++) {
					JSONObject objCus = new JSONObject();
					JSONObject objData = (JSONObject) ay.get(i);
					objCus.put("Name", objData.get("Name").toString());
					objCus.put("Phone", objData.get("Phone").toString());
//					objCus.put("Birth", objData.get("Birth").toString());
					// 車牌
					List<String> ayPlate = Arrays.asList(objData.get("allPlate").toString().split(";"));
					List<String> ayFirst = Arrays.asList(objData.get("allFirst").toString().split(";"));
					List<String> ayBirth = Arrays.asList(objData.get("allBirth").toString().split(";"));
					objCus.put("Birth", ayBirth.get(0));

					objCus.put("allPlate", ayPlate);
					objCus.put("allFirst", ayFirst);

					JSONArray ayMotoData = new JSONArray();
					for (int x = 0; x < ayPlate.size(); x++) {
						JSONArray ayGetMotoData = mssql
								.doQuery("SELECT*FROM TBLMOTOCYCLE WHERE Plate='" + ayPlate.get(x).toString() + "'");
						if (ayGetMotoData.length() > 0) {
							for (int z = 0; z < ayGetMotoData.length(); z++) {
								JSONObject objMoto = new JSONObject();
								if (ayFirst.size() > x) {
									if (ayFirst.get(x).toString().contains("/")) {
										objMoto.put("first", ayFirst.get(x).toString().replaceAll("/", "-"));
									} else {
										objMoto.put("first", ayFirst.get(x).toString());
									}
								} else {
									objMoto.put("first", "");
								}

								objMoto.put("label", ayGetMotoData.getJSONObject(z).get("MLabel"));
								objMoto.put("style", ayGetMotoData.getJSONObject(z).get("MStyle"));
								objMoto.put("color", ayGetMotoData.getJSONObject(z).get("MColor"));
								objMoto.put("engine", ayGetMotoData.getJSONObject(z).get("MEngine"));
								objMoto.put("cc", ayGetMotoData.getJSONObject(z).get("MCc"));
								objMoto.put("plate", ayGetMotoData.getJSONObject(z).get("Plate"));
								objMoto.put("plate_disabled", true);

								if (ayGetMotoData.getJSONObject(z).get("MHere").toString().equals("否")
										|| ayGetMotoData.getJSONObject(z).get("MHere").toString().contains("N")) {
									objMoto.put("buy", "N");
									objMoto.put("dollar_disabled", true);
								} else if (ayGetMotoData.getJSONObject(z).get("MHere").toString().equals("是")
										|| ayGetMotoData.getJSONObject(z).get("MHere").toString().contains("Y")) {
									objMoto.put("buy", "Y");
									objMoto.put("dollar_disabled", false);
								} else {
									objMoto.put("buy", "N");
									objMoto.put("dollar_disabled", true);
								}
								objMoto.put("dollar", ayGetMotoData.getJSONObject(z).get("MDollar"));
//								objMoto.put("note", ayGetMotoData.getJSONObject(z).get("MLabel"));

								ayMotoData.put(objMoto);
							}
						}

					}
					objCus.put("Moto", ayMotoData);
//					System.out.println(objCus.toString());
					ayCusData.put(objCus);
				}
			}
		} catch (Exception e) {
//			StackTraceElement st = e.getStackTrace()[0];
			String ss = Arrays.toString(e.getStackTrace());
			System.out.println(ss);

//			System.out.println(st.getClassName() + "===>" + st.getFileName() + "===>" + st.getLineNumber() + "===>"
//					+ st.getMethodName());
		}
		oRes.put("data", ayCusData);
		return oRes.toString();
	}

	public String updateCustomer(customerModel req) {
		JSONObject oRes = new JSONObject();
		try {

			List<Map<String, Object>> ayMoto = req.getMoto();
			System.out.println(ayMoto);

			String name = req.getName();
			String birth = req.getBirth();
			String phone = req.getPhone();
			String note = req.getNote();
			for (int i = 0; i < ayMoto.size(); i++) {
//				JSONObject obj=()
				String cc = ayMoto.get(i).get("cc").toString();
				String color = ayMoto.get(i).get("color").toString();
				String buy = ayMoto.get(i).get("buy").toString();
				String style = ayMoto.get(i).get("style").toString();
				String engine = ayMoto.get(i).get("engine").toString();
				String plate = ayMoto.get(i).get("plate").toString();
				String label = ayMoto.get(i).get("label").toString();
				String first = ayMoto.get(i).get("first").toString();
				String dollar = ayMoto.get(i).get("dollar").toString();

				JSONArray ay = new JSONArray();
				int x = 0;
				String sql = "";
				ay = mssql.doQuery("SELECT * FROM TBLCUSTOMER WHERE Plate='" + plate + "'");
				if (ay.length() > 0) {
					sql = "UPDATE TBLCUSTOMER SET Name='" + name + "', Birth='" + birth + "', Phone='" + phone
							+ "', Email='', First='" + first + "', Addr='', Notes='" + note + "' WHERE Plate='" + plate
							+ "'";

				} else {
					sql = "INSERT INTO TBLCUSTOMER VALUES('" + name + "', '" + birth + "','" + plate + "','" + phone
							+ "','','','','" + note + "')";

				}

				x = mssql.doUpdate(sql);
				JSONArray ay2 = new JSONArray();
				ay2 = mssql.doQuery("SELECT * FROM TBLMOTOCYCLE WHERE Plate='" + plate + "'");
				int x2 = 0;
				if (ay2.length() > 0) {
					x2 = mssql.doUpdate(
							"UPDATE TBLMOTOCYCLE SET MLabel='" + label + "', MStyle='" + style + "', MColor='" + color
									+ "', MCc='" + cc + "', MEngine='" + engine + "', MCheck='', MOut='',MHere='" + buy
									+ "',MDollar='" + dollar + "' WHERE Plate='" + plate + "'");
				} else {
					x2 = mssql.doUpdate(
							"INSERT INTO TBLMOTOCYCLE VALUES('" + plate + "', '" + label + "','" + style + "','" + color
									+ "','" + cc + "','" + engine + "','','','" + buy + "','" + dollar + "','')");
				}
				oRes.put("x", x);
				oRes.put("x2", x2);
				oRes.put("ay", ay);
				oRes.put("ay2", ay2);
			}
		} catch (Exception e) {
			StackTraceElement st = e.getStackTrace()[0];

			System.out.println(st.getClassName() + "===>" + st.getFileName() + "===>" + st.getLineNumber() + "===>"
					+ st.getMethodName());
		}

		return oRes.toString();
	}

	public String deleteCustomer(customerModel req) {
		JSONObject oRes = new JSONObject();
		System.out.println("delete Customer===>" + req.getMoto());
		List<Map<String, Object>> ayMoto = req.getMoto();
		try {
			for (int i = 0; i < ayMoto.size(); i++) {
				String plate = ayMoto.get(i).get("plate").toString();
				int x = 0;
				x = mssql.doUpdate("DELETE FROM TBLCUSTOMER WHERE Plate='" + plate + "'");
				int x2 = 0;
				x2 = mssql.doUpdate("DELETE FROM TBLMOTOCYCLE WHERE Plate='" + plate + "'");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return oRes.toString();
	}

}
