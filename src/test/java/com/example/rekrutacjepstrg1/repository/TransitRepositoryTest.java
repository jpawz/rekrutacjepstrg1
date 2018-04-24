package com.example.rekrutacjepstrg1.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.rekrutacjepstrg1.domain.DailyTransit;
import com.example.rekrutacjepstrg1.domain.Transit;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransitRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	TransitRepository transitRepository;

	LocalDate date;

	private double price1 = 150;
	private double price2 = 70;
	private double avgPrice = 110;

	private long distance1 = 40;
	private long distance2 = 60;
	private long totalDist = 100;
	private long avgDistance = 50;

	@Before
	public void setup() {
		date = LocalDate.of(2018, 3, 5);
	}

	@Test
	public void checkDailyTransit() {
		// given
		Transit transit1 = new Transit("any source", "any dest", price1, date);
		transit1.setDistance(distance1);
		Transit transit2 = new Transit("any", "any", price2, date);
		transit2.setDistance(distance2);
		entityManager.persist(transit1);
		entityManager.persist(transit2);

		// when
		List<DailyTransit> dailyTransits = transitRepository
				.findDailyTransits(LocalDate.of(2018, 3, 1), LocalDate.of(2018, 3, 20));

		// then
		assertThat(dailyTransits).isNotNull();
		assertThat(dailyTransits.size()).isEqualTo(1);
		assertThat(dailyTransits.get(0).getDate()).isEqualTo(date);
		assertThat(dailyTransits.get(0).getTotalDistance()).isEqualTo(totalDist);
		assertThat(dailyTransits.get(0).getAvgDistance()).isEqualTo(avgDistance);
		assertThat(dailyTransits.get(0).getAvgPrice()).isEqualTo(avgPrice);
	}
}
