package com.example.rekrutacjepstrg1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.rekrutacjepstrg1.domain.Transit;

public interface TransitRepository extends CrudRepository<Transit, Long> {

	List<Transit> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
