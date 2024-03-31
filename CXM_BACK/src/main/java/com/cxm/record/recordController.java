package com.cxm.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class recordController {
	@Autowired
	recordService recordService;

	@PostMapping("/getRecord")
	public String getRecord(@RequestBody recordModel req) {
		return recordService.getRecord(req);
	}

	@PostMapping("/getRecordDetails")
	public String getRecordDetails(@RequestBody recordModel req) {
		return recordService.getRecordDetails(req);
	}

	@PostMapping("/getKm")
	public String getKm(@RequestBody recordModel req) {
		return recordService.getKm(req);
	}

	@PostMapping("/addKm")
	public String addKm(@RequestBody recordModel req) {
		return recordService.addKm(req);
	}

	@PostMapping("/delKm")
	public String delKm(@RequestBody recordModel req) {
		return recordService.delKm(req);
	}

	@PostMapping("/addRecord")
	public String addRecord(@RequestBody recordModel req) {
		return recordService.addRecord(req);
	}

	@PostMapping("/delRecord")
	public String delRecord(@RequestBody recordModel req) {
		return recordService.delRecord(req);
	}

	@PostMapping("/saveNote")
	public String saveNote(@RequestBody recordModel req) {
		return recordService.saveNote(req);
	}

	@PostMapping("/getNotPay")
	public String getNotPay(@RequestBody recordModel req) {
		return recordService.getNotPay(req);
	}

	@PostMapping("/getRecFixType")
	public String getRecFixType() {
		return recordService.getRecFixType();
	}
}
