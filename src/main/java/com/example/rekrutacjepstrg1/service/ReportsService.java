package com.example.rekrutacjepstrg1.service;

import java.time.LocalDate;
import java.util.Map;

public interface ReportsService {

	Map<String, String> getDailyReport(LocalDate startDate, LocalDate endDate);
}
