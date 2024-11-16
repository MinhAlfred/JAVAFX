package org.example.hsf301.repos;

import java.util.List;
import org.example.hsf301.daos.ITourDAO;
import org.example.hsf301.daos.TourDAO;
import org.example.hsf301.pojos.Tours;

public class TourRepository implements ITourRepository {

    private final ITourDAO tourDAO;

    public TourRepository(String jpaName){
        tourDAO = new TourDAO(jpaName);
    }

    @Override
    public void save(Tours tours) {
        tourDAO.save(tours);
    }

    @Override
    public List<Tours> findAll() {
        return tourDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        tourDAO.delete(id);
    }

    @Override
    public Tours findById(Long id) {
        return tourDAO.findById(id);
    }

    @Override
    public void update(Tours tours) {
         tourDAO.update(tours);
    }

    @Override
    public List<Tours> findByTourName(String tourName) {
        return tourDAO.findByTourName(tourName);
    }

    @Override
    public List<Tours> findByTourActive() {
        return tourDAO.findByTourActive();
    }
}
