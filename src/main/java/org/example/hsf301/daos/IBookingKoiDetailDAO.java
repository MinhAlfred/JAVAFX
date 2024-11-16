package org.example.hsf301.daos;

import org.example.hsf301.pojos.BookingKoiDetail;

import java.util.List;

public interface IBookingKoiDetailDAO {
    List<BookingKoiDetail> getAll();

    List<BookingKoiDetail> findByBookingId(Long bookingId);

    void save(BookingKoiDetail student);

    void delete(Long studentID);

    BookingKoiDetail findById(Long studentID);

    void update(BookingKoiDetail student);
}
