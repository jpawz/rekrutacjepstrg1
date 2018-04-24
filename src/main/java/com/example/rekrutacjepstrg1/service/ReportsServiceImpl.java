package com.example.rekrutacjepstrg1.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rekrutacjepstrg1.domain.DailyTransit;
import com.example.rekrutacjepstrg1.domain.Transit;
import com.example.rekrutacjepstrg1.repository.TransitRepository;

@Service
public class ReportsServiceImpl implements ReportsService {

	private static final String DISTANCE_UNIT = "km";
	private static final String CURRENCY = "PLN";

	private final TransitRepository transitRepository;

	@Autowired
	public ReportsServiceImpl(TransitRepository transitRepository) {
		this.transitRepository = transitRepository;
	}

	@Override
	public Map<String, String> getDailyReport(LocalDate startDate, LocalDate endDate) {
		List<Transit> transits = transitRepository.findByDateBetween(startDate, endDate);
		long totalDistance = 0;
		double totalPrice = 0;
		for (Transit transit : transits) {
			totalDistance += transit.getDistance();
			totalPrice += transit.getPrice();
		}
		Map<String, String> report = new HashMap<>();
		report.put("total_distance", totalDistance + DISTANCE_UNIT);
		report.put("total_price", totalPrice + CURRENCY);
		return report;
	}

	@Override
	public List<DailyTransit> getMonthlyReport(LocalDate startDate, LocalDate endDate) {
		return transitRepository.findDailyTransits(startDate, endDate);
	}

}
