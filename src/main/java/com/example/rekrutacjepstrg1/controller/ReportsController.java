package com.example.rekrutacjepstrg1.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rekrutacjepstrg1.service.ReportsService;

@RestController
@RequestMapping("reports")
public class ReportsController {

	private final ReportsService reportsService;

	@Autowired
	public ReportsController(ReportsService reportsService) {
		this.reportsService = reportsService;
	}

	@GetMapping("daily")
	public ResponseEntity<Map<String, String>> getDailyReport(
			@RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
			@RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {

		return new ResponseEntity<>(reportsService.getDailyReport(
				LocalDate.parse(startDate), LocalDate.parse(endDate)), HttpStatus.OK);
	}
}
