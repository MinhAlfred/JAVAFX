package org.example.hsf301.repos;

import org.example.hsf301.daos.DeliveryDAO;
import org.example.hsf301.daos.IDeliveryDAO;
import org.example.hsf301.pojos.Delivery;

import java.util.List;

public class DeliveryRepository implements IDeliveryRepository {
    private final IDeliveryDAO dao;
    public DeliveryRepository(String name) {
        dao = new DeliveryDAO(name);
    }
    @Override
    public List<Delivery> getAll() {
        return dao.getAll();
    }

    @Override
    public void save(Delivery student) {
        dao.save(student);
    }

    @Override
    public void delete(Long studentID) {
        dao.delete(studentID);
    }

    @Override
    public Delivery findById(Long studentID) {
        return dao.findById(studentID);
    }

    @Override
    public void update(Delivery student) {
        dao.update(student);
    }
}
