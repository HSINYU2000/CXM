package com.cxm.employee;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxm.database.mssql;

@Service
public class employeeService {
	@Autowired
	mssql mssql;

	public String getEmployee() {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ayEmployee = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT * FROM TBLEMPLOYEE");
			for (int i = 0; i < ay.length(); i++) {
				JSONObject obj = new JSONObject();

				obj.put("label", ay.getJSONObject(i).optString("NAME"));
				obj.put("value", ay.getJSONObject(i).optString("ACC"));
				ayEmployee.put(obj);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("data", ay);
		oRes.put("employeeOpt", ayEmployee);
		return oRes.toString();
	}

	public String getRole() {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ayRole = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT * FROM TBLROLE");
			for (int i = 0; i < ay.length(); i++) {
				JSONObject obj = new JSONObject();

				obj.put("label", ay.getJSONObject(i).optString("ROLE_NAME"));

				obj.put("value", ay.getJSONObject(i).optString("ROLEID"));
				ayRole.put(obj);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("data", ay);
		oRes.put("roleOpt", ayRole);
		return oRes.toString();
	}

	public String addEmployee(employeeModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		int add = 0;

		try {
			ay = mssql.doQuery("SELECT * FROM TBLEMPLOYEE WHERE ACC='" + req.getAcc() + "'");
			if (ay.length() > 0) {
				add = 2;
			} else {
				add = mssql.doUpdate("INSERT INTO TBLEMPLOYEE VALUES('" + req.getName() + "','" + req.getAcc() + "','"
						+ req.getPass() + "','" + req.getBirth() + "','" + req.getPhone() + "','" + req.getDate()
						+ "','" + req.getRole() + "')");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("result", String.valueOf(add));
		return oRes.toString();
	}

	public String editEmployee(employeeModel req) {
		JSONObject oRes = new JSONObject();
		int edit = 0;
		try {
			edit = mssql.doUpdate("UPDATE TBLEMPLOYEE SET NAME='" + req.getName() + "',BIRTH='" + req.getBirth()
					+ "',PHONE='" + req.getPhone() + "',DATE='" + req.getDate() + "',ROLE='" + req.getRole()
					+ "' WHERE ACC='" + req.getAcc() + "'");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("result", String.valueOf(edit));
		return oRes.toString();
	}

	public String delEmployee(employeeModel req) {
		JSONObject oRes = new JSONObject();
		int del = 0;
		try {
			del = mssql.doUpdate("DELETE FROM TBLEMPLOYEE WHERE NAME='" + req.getName() + "' AND ACC='" + req.getAcc()
					+ "' AND BIRTH='" + req.getBirth() + "'");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("result", String.valueOf(del));
		return oRes.toString();
	}

	public String checkPass(employeeModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		String pass = "";

		try {
			ay = mssql.doQuery("SELECT * FROM TBLEMPLOYEE WHERE ACC='" + req.getAcc() + "'");
			if (ay.length() > 0) {
				pass = ay.getJSONObject(0).get("PASS").toString();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("Pass", pass);
		return oRes.toString();
	}

	public String passReset(employeeModel req) {
		JSONObject oRes = new JSONObject();
		int reset = 0;
		try {
			reset = mssql.doUpdate("UPDATE TBLEMPLOYEE SET PASS='" + req.getPass() + "' WHERE ACC='" + req.getAcc()
					+ "' AND NAME='" + req.getName() + "' AND PHONE='" + req.getPhone() + "'");
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("result", String.valueOf(reset));
		return oRes.toString();
	}
}
