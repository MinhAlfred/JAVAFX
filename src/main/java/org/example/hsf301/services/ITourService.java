package org.example.hsf301.services;


import org.example.hsf301.dtos.request.TourRequest;
import org.example.hsf301.pojos.Tours;

import java.util.List;

public interface ITourService {

    Tours addTour(TourRequest tourRequest);
    Tours updateTour(Long tourId, TourRequest tourRequest) throws Exception;
    void deleteTour(Long tourId);
    Tours getTour(Long tourId);
    List<Tours> findAll();
    List<Tours> findByName(String tourName);
    List<Tours> findByTourActive();

}
