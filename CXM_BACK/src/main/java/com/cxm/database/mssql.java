package com.cxm.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class mssql {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public JSONArray doQuery(String sql) {
		System.out.println("sql===>" + sql);
//		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		List<Map<String, Object>> ayQuery = new ArrayList<Map<String, Object>>();
		if (!sql.equals("")) {
			ayQuery = jdbcTemplate.queryForList(sql);
		}
//		System.out.println("ayQuery" + ayQuery);
		if (ayQuery.size() > 0) {
			for (Map<String, Object> map : ayQuery) {
				JSONObject obj = new JSONObject();

				for (String key : map.keySet()) {
					if (map.get(key) != null) {
						obj.put(key, map.get(key));

					} else {
						obj.put(key, "");
					}

//					System.out.println("key" + map.get(key));
				}

				ay.put(obj);
			}
		}
//		System.out.println(sql + "===>");
//		System.out.println(ay);
//		oRes.put("data", ay);
		return ay;
	}

	public Integer doUpdate(String sql) {
		int iUpdate = 0;
		System.out.println("sql===>" + sql);
		if (!sql.equals("")) {
			iUpdate = jdbcTemplate.update(sql);
		}

		return iUpdate;
	}
}
