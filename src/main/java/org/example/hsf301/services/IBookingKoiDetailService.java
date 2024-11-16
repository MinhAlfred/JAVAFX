package org.example.hsf301.services;

import org.example.hsf301.dtos.request.BookingKoiDetailRequest;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.BookingKoiDetail;

import java.util.List;

public interface IBookingKoiDetailService {
    BookingKoiDetail createKoiDetail(BookingKoiDetailRequest bookingKoiDetailRequest, Long bookingId);

    void deletebyBookingKoiDetail(Long bookingKoiDetailId, Account staff);

    List<BookingKoiDetail> bookingKoiDetails(Long bookingID);

    BookingKoiDetail updateBookingKoiDetail(Long id, BookingKoiDetailRequest BookingKoiDetailRequest,Account staff);
}
