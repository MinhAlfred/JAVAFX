package org.example.hsf301.daos;

import org.example.hsf301.pojos.BookingKoiDetail;
import org.example.hsf301.pojos.Bookings;

import java.util.List;

public interface IBookingDAO {
    List<Bookings> findAll();
    void save(Bookings booking);
    void delete(Long bookingID);
    Bookings findById(Long bookingID);
    void update(Bookings booking);
    List<Bookings> findByAccountID(String accountID);
    List<BookingKoiDetail> findByBookingID(Long bookingID);
}
