package com.example.rekrutacjepstrg1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.rekrutacjepstrg1.domain.DailyTransit;

public interface ReportsService {

	Map<String, String> getDailyReport(LocalDate startDate, LocalDate endDate);

	List<DailyTransit> getMonthlyReport(LocalDate startDate, LocalDate endDate);
}