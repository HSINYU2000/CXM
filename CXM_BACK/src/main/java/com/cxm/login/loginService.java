package com.cxm.login;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxm.database.mssql;

@Service
public class loginService {
	@Autowired
	mssql mssql;

	public String login(loginModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ay2 = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT * FROM TBLEMPLOYEE WHERE ACC='" + req.getAcc() + "'");
			if (ay.length() > 0) {
				ay2 = mssql.doQuery("SELECT * FROM TBLFUNC_LIST WHERE ROLEID='" + ay.optJSONObject(0).opt("ROLE")
						+ "' ORDER BY ORDER_LIST ASC");

				ay.optJSONObject(0).put("funcList", ay2);
			}

		} catch (Exception e) {
			StackTraceElement st = e.getStackTrace()[0];

			System.out.println(st.getClassName() + "===>" + st.getFileName() + "===>" + st.getLineNumber() + "===>"
					+ st.getMethodName());
		}
		oRes.put("data", ay);

		return oRes.toString();
	}
}
