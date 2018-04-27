package com.example.rekrutacjepstrg1.service;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rekrutacjepstrg1.domain.Transit;
import com.example.rekrutacjepstrg1.repository.TransitRepository;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

@Service
public class TransitServiceImpl implements TransitService {

  TransitRepository transitRepository;

  @Autowired
  private DistanceMatrixApiRequest distanceMatrixApiRequest;

  @Autowired
  private GeoApiContext context;

  private String API_KEY = "";
  private Transit transit;

  @Autowired
  public TransitServiceImpl(TransitRepository repository) {
    this.transitRepository = repository;
  }

  @Override
  public Transit createTransit(String sourceAddress, String destinationAddress, double price,
      LocalDate date) throws InterruptedException {

    context = new GeoApiContext.Builder().apiKey(API_KEY).build();
    final CountDownLatch latch = new CountDownLatch(1);
    distanceMatrixApiRequest = DistanceMatrixApi.newRequest(context);
    distanceMatrixApiRequest.origins(sourceAddress).destinations(destinationAddress)
        .mode(TravelMode.DRIVING).units(Unit.METRIC)
        .setCallback(new PendingResult.Callback<DistanceMatrix>() {

          @Override
          public void onResult(DistanceMatrix result) {
            if (statusIsOK(result)) {
              transit = new Transit(sourceAddress, destinationAddress, price, date);
              transit.setDistance(distanceInKm(result));
              transitRepository.save(transit);
            }
            latch.countDown();
          }

          @Override
          public void onFailure(Throwable e) {
            transit = null;
            e.printStackTrace();
            latch.countDown();
          }
        });

    latch.await();

    return transit;
  }

  private boolean statusIsOK(DistanceMatrix result) {
    return result.rows[0].elements[0].status == DistanceMatrixElementStatus.OK;
  }

  private long distanceInKm(DistanceMatrix result) {
    return result.rows[0].elements[0].distance.inMeters / 1_000;
  }

}
