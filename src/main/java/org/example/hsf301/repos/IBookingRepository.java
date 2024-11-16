package org.example.hsf301.repos;

import org.example.hsf301.pojos.BookingKoiDetail;
import org.example.hsf301.pojos.Bookings;

import java.util.List;

public interface IBookingRepository {
    List<Bookings> findAll();
    void save(Bookings student);
    void delete(Long studentID);
    Bookings findById(Long studentID);
    void update(Bookings student);
    List<Bookings> findByAccountID(String accountID);
    List<BookingKoiDetail> findByBookingID(Long bookingID);
}
