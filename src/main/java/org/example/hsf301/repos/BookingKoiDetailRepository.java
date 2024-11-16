
package org.example.hsf301.repos;

import org.example.hsf301.daos.BookingKoiDetailDAO;
import org.example.hsf301.daos.IBookingKoiDetailDAO;
import org.example.hsf301.pojos.BookingKoiDetail;

import java.util.List;

public class BookingKoiDetailRepository implements IBookingKoiDetailRepository {
    private final IBookingKoiDetailDAO dao;
    public BookingKoiDetailRepository(String name) {
        dao = new BookingKoiDetailDAO(name);
    }
    @Override
    public List<BookingKoiDetail> getAll() {
        return dao.getAll();
    }

    @Override
    public void save(BookingKoiDetail student) {
        dao.save(student);
    }

    @Override
    public void delete(Long studentID) {
        dao.delete(studentID);
    }

    @Override
    public List<BookingKoiDetail> findByBookingId(Long bookingId) {
        return dao.findByBookingId(bookingId);
    }

    @Override
    public BookingKoiDetail findById(Long studentID) {
        return dao.findById(studentID);
    }

    @Override
    public void update(BookingKoiDetail student) {
        dao.update(student);
    }
}
