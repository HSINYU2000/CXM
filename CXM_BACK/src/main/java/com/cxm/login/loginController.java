package com.cxm.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {
	@Autowired
	loginService loginService;

	@PostMapping("/login")
	public String login(@RequestBody loginModel req) {
		return loginService.login(req);
	}
}
