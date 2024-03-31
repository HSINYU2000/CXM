package com.cxm.fixItem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxm.database.mssql;

@Service
public class fixItemService {
	@Autowired
	mssql mssql;

	public String getType() {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ayType = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT DISTINCT ITEM_TYPE FROM TBLFIX_ITEM");
			if (ay.length() > 0) {

				for (int i = 0; i < ay.length(); i++) {
					JSONObject obj2 = new JSONObject();
					JSONObject oType = ay.getJSONObject(i);
					obj2.put("value", oType.opt("ITEM_TYPE"));
					obj2.put("label", oType.opt("ITEM_TYPE"));
					ayType.put(obj2);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("optType", ayType);
		return oRes.toString();
	}

	public String getItem(fixItemModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		JSONArray ayItem = new JSONArray();
		try {
			ay = mssql.doQuery("SELECT * FROM TBLFIX_ITEM WHERE ITEM_TYPE='" + req.getType() + "'");
			if (ay.length() > 0) {

				for (int i = 0; i < ay.length(); i++) {
					JSONObject obj2 = new JSONObject();
					JSONObject oItem = ay.getJSONObject(i);
					obj2.put("value", oItem.opt("ITEM"));
					obj2.put("label", oItem.opt("ITEM"));
					obj2.put("price", oItem.opt("ITEM_PRICE"));
					obj2.put("cost", oItem.opt("ITEM_COST"));
					ayItem.put(obj2);
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("optItem", ayItem);
		return oRes.toString();
	}

	public String getFixData() {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();

		try {
			ay = mssql.doQuery("SELECT * FROM TBLFIX_ITEM");

		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("fixData", ay);
		return oRes.toString();
	}

	public String updateFix(fixItemModel req) {
		JSONObject oRes = new JSONObject();
		JSONArray ay = new JSONArray();
		int i = 0;
		String mess = "";
		System.out.println(req.getStatus());
		try {
			if (req.getStatus().equals("insert")) {
				ay = mssql.doQuery("SELECT * FROM TBLFIX_ITEM WHERE ITEM_TYPE='" + req.getType() + "' AND ITEM='"
						+ req.getItem() + "' AND ITEM_PRICE='" + req.getPrice() + "'");
				if (ay.length() > 0) {
					mess = "項目已存在";
					oRes.put("mess", mess);
					oRes.put("type", "0");
					return oRes.toString();
				}
				i = mssql.doUpdate("INSERT INTO TBLFIX_ITEM (ITEM_TYPE,ITEM,ITEM_PRICE,ITEM_COST,ITEM_STOCK)VALUES ('"
						+ req.getType() + "','" + req.getItem() + "','" + req.getPrice() + "','" + req.getCost() + "','"
						+ req.getStock() + "')");
				mess = "項目已新增";
				oRes.put("mess", mess);
				oRes.put("type", "1");
			} else {
				i = mssql.doUpdate("UPDATE TBLFIX_ITEM SET ITEM_PRICE='" + req.getPrice() + "',ITEM_STOCK='"
						+ req.getStock() + "',ITEM_COST='" + req.getCost() + "' WHERE ITEM_TYPE='" + req.getType()
						+ "' AND ITEM='" + req.getItem() + "' AND ITEM_ID='" + req.getID() + "'");
				mess = "項目內容已更新";
				oRes.put("mess", mess);
				oRes.put("type", "1");
//				return oRes.toString();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return oRes.toString();
	}

	public String delFix(fixItemModel req) {
		JSONObject oRes = new JSONObject();
		String mess = "";
		int i = 0;
		try {
			i = mssql.doUpdate("DELETE FROM TBLFIX_ITEM WHERE ITEM_TYPE='" + req.getType() + "' AND ITEM='"
					+ req.getItem() + "' AND ITEM_PRICE='" + req.getPrice() + "' AND ITEM_COST='" + req.getCost()
					+ "' AND ITEM_ID='" + req.getID() + "'");
			if (i > 0) {
				mess = "刪除成功";
			} else {
				mess = "刪除失敗";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		oRes.put("res", i);
		oRes.put("mess", mess);
		return oRes.toString();
	}
}
