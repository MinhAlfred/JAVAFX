package org.example.hsf301.services;

import java.util.List;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.pojos.BookingKoiDetail;
import org.example.hsf301.pojos.Bookings;

public interface IBookingService {
    List<Bookings> findAll();
    void save(Bookings student);
    void delete(Long studentID);
    Bookings findById(Long studentID);
    void update(Bookings student);
    List<Bookings> findByAccountID(String accountID);
    List<BookingKoiDetail> findByBookingID(Long bookingID);
    void updateBookingStatus(Long bookingID, PaymentStatus status);
}
