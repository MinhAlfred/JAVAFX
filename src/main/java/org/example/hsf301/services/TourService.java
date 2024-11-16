package org.example.hsf301.services;

import org.example.hsf301.dtos.request.TourRequest;
import org.example.hsf301.pojos.Tours;
import org.example.hsf301.repos.ITourRepository;
import org.example.hsf301.repos.TourRepository;
import java.util.List;

public class    TourService implements ITourService {
    private final ITourRepository tourRepository;
    public TourService(String name) {
        tourRepository = new TourRepository(name);
    }
    @Override
    public Tours addTour(TourRequest tourRequest) {
        Tours tour = convertToEntity(tourRequest);
        try {
            if (tourRequest == null) {
                throw new Exception();
            }
            tour.setStatus("active");
            tour.setRemaining(tour.getMaxParticipants());
            tourRepository.save(tour);
            return tour;
        }catch (Exception e) {
            throw new RuntimeException("Fail to save tour"+ e.getMessage());
        }

    }



    @Override
    public Tours updateTour(Long tourId, TourRequest tourRequest) throws Exception {
        Tours tour = tourRepository.findById(tourId);
        if(tour == null) {
            throw new IllegalArgumentException("Tour not found");
        }
        if(tourRequest == null) {
            throw new IllegalArgumentException("TourRequest not found");
        }
        tour.setTourName(tourRequest.getTourName());
        tour.setDescription(tourRequest.getDescription());
        tour.setStartTime(tourRequest.getStartTime());
        tour.setEndTime(tourRequest.getEndTime());
        tour.setTourImg(tourRequest.getTourImg());
        tour.setUnitPrice(tourRequest.getUnitPrice());
        tour.setMaxParticipants(tourRequest.getMaxParticipants());
        tourRepository.update(tour);
        return tour;
    }

    @Override
    public void deleteTour(Long tourId) {
        Tours tour = tourRepository.findById(tourId);
        if(tour == null) {
            throw new IllegalArgumentException("Tour not found");
        }else{
            tourRepository.delete(tourId);
        }
    }

    @Override
    public Tours getTour(Long tourId) {
        Tours tour = tourRepository.findById(tourId);
        if(tour == null) {
            throw new IllegalArgumentException("Tour not found");
        }
        return tour;

    }

    @Override
    public List<Tours> findAll() {
        List<Tours> tours = tourRepository.findAll();
        if (tours == null) {
            throw new IllegalArgumentException("No Tours found");
        }
        return tours;
    }

    @Override
    public List<Tours> findByName(String tourName) {
        List<Tours> tours = tourRepository.findByTourName(tourName);
        if (tours == null) {
            throw new IllegalArgumentException("No Tours found");
        }
        return tours;
    }
    @Override
    public List<Tours> findByTourActive() {
        List<Tours> tours = tourRepository.findByTourActive();
        if (tours == null) {
            throw new IllegalArgumentException("No Tours found");
        }
        return tours;
    }



    private Tours convertToEntity(TourRequest tourRequest) {
        Tours tour = new Tours();
        if (tourRequest == null) {
            throw new IllegalArgumentException("tourRequest can not be null");
        }
        tour.setDescription(tourRequest.getDescription());
        tour.setTourImg(tourRequest.getTourImg());
        tour.setTourName(tourRequest.getTourName());
        tour.setEndTime(tourRequest.getEndTime());
        tour.setStartTime(tourRequest.getStartTime());
        tour.setUnitPrice(tourRequest.getUnitPrice());
        tour.setMaxParticipants(tourRequest.getMaxParticipants());
        return tour;
    }

}
