package com.cxm.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class employeeController {

	@Autowired
	employeeService employeeService;

	@PostMapping("/getEmployee")
	public String getEmployee() {
		return employeeService.getEmployee();
	}

	@PostMapping("/addEmployee")
	public String addEmployee(@RequestBody employeeModel req) {
		return employeeService.addEmployee(req);
	}

	@PostMapping("/editEmployee")
	public String editEmployee(@RequestBody employeeModel req) {
		return employeeService.editEmployee(req);
	}

	@PostMapping("/checkPass")
	public String checkPass(@RequestBody employeeModel req) {
		return employeeService.checkPass(req);
	}

	@PostMapping("/passReset")
	public String passReset(@RequestBody employeeModel req) {
		return employeeService.passReset(req);
	}

	@PostMapping("/delEmployee")
	public String delEmployee(@RequestBody employeeModel req) {
		return employeeService.delEmployee(req);
	}

	@PostMapping("/getRole")
	public String getRole() {
		return employeeService.getRole();
	}
}
