package com.cxm.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class customerController {

	@Autowired
	customerService customerService;

	@PostMapping("/getCustomer")
	public String getCustomer() {
		return customerService.getCustomer();
	}

	@PostMapping("/updateCustomer")
	public String updateCustomer(@RequestBody customerModel req) {
		return customerService.updateCustomer(req);
	}

	@PostMapping("/deleteCustomer")
	public String deleteCustomer(@RequestBody customerModel req) {
		return customerService.deleteCustomer(req);
	}
}
