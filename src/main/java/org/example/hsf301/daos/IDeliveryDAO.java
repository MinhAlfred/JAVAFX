package org.example.hsf301.daos;

import org.example.hsf301.pojos.Delivery;

import java.util.List;

public interface IDeliveryDAO {
    List<Delivery> getAll();

    void save(Delivery student);

    void delete(Long studentID);

    Delivery findById(Long studentID);

    void update(Delivery student);

}
