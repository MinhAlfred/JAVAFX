package org.example.hsf301.repos;

import org.example.hsf301.pojos.BookingKoiDetail;

import java.util.List;

public interface IBookingKoiDetailRepository {
    List<BookingKoiDetail> getAll();

    void save(BookingKoiDetail student);

    void delete(Long studentID);

    List<BookingKoiDetail> findByBookingId(Long bookingId);

    BookingKoiDetail findById(Long studentID);

    void update(BookingKoiDetail student);
}
