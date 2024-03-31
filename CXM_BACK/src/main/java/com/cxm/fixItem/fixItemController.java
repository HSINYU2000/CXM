package com.cxm.fixItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class fixItemController {
	@Autowired
	fixItemService fixItemService;

	@PostMapping("/getFixType")
	public String getType() {
		return fixItemService.getType();
	}

	@PostMapping("/getFixItem")
	public String getItem(@RequestBody fixItemModel req) {
		return fixItemService.getItem(req);
	}

	@PostMapping("/getFixData")
	public String getFixData() {
		return fixItemService.getFixData();
	}

	@PostMapping("/updateFix")
	public String updateFix(@RequestBody fixItemModel req) {
		System.out.println("updatFix");
		return fixItemService.updateFix(req);
	}

	@PostMapping("/delFix")
	public String delFix(@RequestBody fixItemModel req) {
		return fixItemService.delFix(req);
	}
}
