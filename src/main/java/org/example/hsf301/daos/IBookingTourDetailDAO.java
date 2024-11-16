package org.example.hsf301.daos;

import org.example.hsf301.pojos.BookingTourDetail;

import java.util.List;

public interface IBookingTourDetailDAO {
    List<BookingTourDetail> getAll();

    void save(BookingTourDetail bookingTourDetail);
    List<BookingTourDetail> findByBookingId(Long bookingId);

    void delete(Long id);

    BookingTourDetail findById(Long id);

    void update(BookingTourDetail bookingTourDetail);
}
