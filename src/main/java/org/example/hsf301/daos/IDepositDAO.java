package org.example.hsf301.daos;

import org.example.hsf301.pojos.Deposit;

import java.util.List;

public interface IDepositDAO {
    List<Deposit> getAll();

    void save(Deposit student);

    void delete(Long studentID);

    Deposit findById(Long studentID);

    void update(Deposit student);

    Deposit findByBookingId(Long bookingId);
}
