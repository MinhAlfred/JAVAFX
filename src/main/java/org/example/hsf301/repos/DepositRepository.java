package org.example.hsf301.repos;

import org.example.hsf301.daos.DepositDAO;
import org.example.hsf301.daos.IDepositDAO;
import org.example.hsf301.pojos.Deposit;

import java.util.List;

public class DepositRepository implements IDepositRepository {
    private final IDepositDAO dao;
    public DepositRepository(String name) {
        dao = new DepositDAO(name);
    }

    @Override
    public List<Deposit> getAll() {
        return dao.getAll();
    }

    @Override
    public void save(Deposit student) {
       dao.save(student);
    }

    @Override
    public void delete(Long studentID) {
        dao.delete(studentID);
    }

    @Override
    public Deposit findById(Long studentID) {
        return dao.findById(studentID);
    }

    @Override
    public void update(Deposit student) {
        dao.update(student);
    }

    @Override
    public Deposit findByBookingId(Long bookingId) {
        return dao.findByBookingId(bookingId);
    }
}
