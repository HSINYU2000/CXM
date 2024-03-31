package com.cxm.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class financeController {
	@Autowired
	financeService financeService;

	@PostMapping("/getDaySum")
	public String getDaySum() {
		return financeService.getDaySum();
	}
}
