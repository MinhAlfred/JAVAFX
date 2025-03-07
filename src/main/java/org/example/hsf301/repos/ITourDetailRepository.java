package org.example.hsf301.repos;

import org.example.hsf301.pojos.TourDetail;


import java.util.List;

public interface ITourDetailRepository {
    void save(TourDetail tourDetail);
    List<TourDetail> findAll();
    void delete(Long id);
    TourDetail findById(Long id);
    void update(TourDetail tourDetail);
}
