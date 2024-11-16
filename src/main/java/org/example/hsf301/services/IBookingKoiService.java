package org.example.hsf301.services;

import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.dtos.request.BookingKoiRequest;
import org.example.hsf301.dtos.request.BookingUpdate;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.Bookings;

import java.util.List;

public interface IBookingKoiService {
    Bookings createKoiBooking(BookingKoiRequest request, Long bookingTourId, Account account);

    Bookings getKoiBookings(Long bookingId);

    Bookings updateKoiBooking(BookingUpdate update, Long bookingId,Account account);

    List<Bookings> getAllKoiBookings();

    List<Bookings> getAllTourBookings();

    List<Bookings> getAllKoiBookings(String username);

    Bookings deleteKoiBooking(Long bookingId);

    List<Bookings> getAllKoiBookingStatus(PaymentStatus paymentStatus);
    List<Bookings> getAllTourBookingStatus(PaymentStatus paymentStatus);
}
