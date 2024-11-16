package org.example.hsf301.repos;

import org.example.hsf301.daos.BookingDAO;
import org.example.hsf301.daos.IBookingDAO;
import org.example.hsf301.pojos.BookingKoiDetail;
import org.example.hsf301.pojos.Bookings;

import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final IBookingDAO dao;
    public BookingRepository(String name) {
        dao = new BookingDAO(name);
    }
    @Override
    public List<Bookings> findAll() {
        return dao.findAll();
    }

    @Override
    public void save(Bookings student) {
        dao.save(student);
    }

    @Override
    public void delete(Long studentID) {
        dao.delete(studentID);
    }

    @Override
    public Bookings findById(Long studentID) {
        return dao.findById(studentID);
    }

    @Override
    public void update(Bookings student) {
        dao.update(student);
    }

    @Override
    public List<Bookings> findByAccountID(String accountID) {
        return dao.findByAccountID(accountID);
    }

    @Override
    public List<BookingKoiDetail> findByBookingID(Long bookingID) {
        return dao.findByBookingID(bookingID);
    }

}
