package com.example.rekrutacjepstrg1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.rekrutacjepstrg1.domain.DailyTransit;
import com.example.rekrutacjepstrg1.domain.Transit;

public interface TransitRepository extends CrudRepository<Transit, Long> {

	List<Transit> findByDateBetween(LocalDate startDate, LocalDate endDate);

	@Query("SELECT new com.example.rekrutacjepstrg1.domain." //
			+ "DailyTransit(t.date, SUM(t.distance), CAST(ROUND(AVG(t.distance), 0) AS long), AVG(t.price)) " //
			+ "FROM transit t " //
			+ "WHERE t.date BETWEEN :start AND :end " //
			+ "GROUP BY t.date") //
	List<DailyTransit> findDailyTransits(@Param("start") LocalDate startDate,
			@Param("end") LocalDate endDate);
}
