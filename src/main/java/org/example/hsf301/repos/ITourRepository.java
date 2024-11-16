package org.example.hsf301.repos;

import java.util.List;
import org.example.hsf301.pojos.Tours;

public interface ITourRepository {
    void save(Tours tours);
    List<Tours> findAll();
    void delete(Long id);
    Tours findById(Long id);
    void update(Tours tours);
    List<Tours> findByTourName(String tourName);
    List<Tours> findByTourActive();
}
