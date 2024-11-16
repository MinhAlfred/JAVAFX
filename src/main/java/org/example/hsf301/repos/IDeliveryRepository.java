package org.example.hsf301.repos;

import org.example.hsf301.pojos.Delivery;

import java.util.List;

public interface IDeliveryRepository {
    List<Delivery> getAll();

    void save(Delivery student);

    void delete(Long studentID);

    Delivery findById(Long studentID);

    void update(Delivery student);


}
