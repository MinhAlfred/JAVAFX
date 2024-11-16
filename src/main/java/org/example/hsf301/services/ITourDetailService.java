package org.example.hsf301.services;

import org.example.hsf301.dtos.request.TourDetailRequest;
import org.example.hsf301.pojos.TourDetail;

import java.util.List;

public interface ITourDetailService {
    TourDetail addTourDetail(TourDetailRequest tourDetailRequest);

    TourDetail updateTourDetailHistory(Long tourDetailId, TourDetailRequest tourDetailRequest) throws Exception;

    void deleteTourDetail(Long tourDetailId);

    TourDetail getTourDetail(Long tourDetailId);

    List<TourDetail> getAllTourDetails();
}
