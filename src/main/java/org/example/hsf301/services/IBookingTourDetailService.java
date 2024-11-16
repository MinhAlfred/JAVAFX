package org.example.hsf301.services;



import org.example.hsf301.dtos.request.BookingTourDetailRequest;
import org.example.hsf301.pojos.BookingTourDetail;

import java.util.List;

public interface IBookingTourDetailService {
    BookingTourDetail addBookingTourDetail(BookingTourDetailRequest bookingTourDetailRequest);

    BookingTourDetail updateBookingTourDetail(Long bookingTourDetailId, BookingTourDetailRequest bookingTourDetailRequest) throws Exception;

    void deleteBookingTourDetail(Long bookingTourDetailId);

    BookingTourDetail getBookingTourDetail(Long bookingTourDetailId);

    List<BookingTourDetail> getAllBookingTourDetails();
    List<BookingTourDetail> bookingTourDetails(Long bookingID);

}
