package org.example.hsf301.repos;

import org.example.hsf301.pojos.BookingTourDetail;

import java.util.List;

public interface IBookingTourDetailRepository {
    List<BookingTourDetail> getAll();

    void save(BookingTourDetail bookingTourDetail);

    void delete(Long id);

    BookingTourDetail findById(Long id);

    void update(BookingTourDetail bookingTourDetail);
    List<BookingTourDetail> findByBookingId(Long bookingId);
}
