package com.example.rekrutacjepstrg1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rekrutacjepstrg1.domain.Transit;
import com.example.rekrutacjepstrg1.repository.TransitRepository;

public class TransitServiceImplTest {

  @Mock
  TransitRepository mockRepository;

  TransitServiceImpl transitService;
  private String sourceAddress = "ul. Zakręt 8, Poznań";
  private String destinationAddress = "Złota 44, Warszawa";
  private double price = 450;
  private LocalDate date = LocalDate.of(2018, 3, 15);

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    transitService = new TransitServiceImpl(mockRepository);
  }

  @Test
  public void checkCreateTransit() throws InterruptedException {
    when(mockRepository.save(any(Transit.class))).thenReturn(any());

    Transit transit = transitService.createTransit(sourceAddress, destinationAddress, price, date);

    assertThat(transit).isNotNull();
    assertThat(transit.getDistance()).isBetween(200L, 500L);
  }

  @Test
  public void wrongTransitShouldNotBeSaved() throws InterruptedException {
    transitService.createTransit("wrong source", "wrong dest", price, date);

    verify(mockRepository, never()).save(any(Transit.class));
  }
}
