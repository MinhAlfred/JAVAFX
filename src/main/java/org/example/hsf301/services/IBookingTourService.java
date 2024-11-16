package org.example.hsf301.services;

import org.example.hsf301.dtos.request.BookingTourRequest;
import org.example.hsf301.dtos.request.BookingUpdate;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.Bookings;

import java.util.List;

public interface IBookingTourService {
    Bookings createTourBooking(BookingTourRequest request, Account account);

    Bookings getTourBookings(Long bookingId);

    Bookings updateTourBooking(BookingUpdate update, Long bookingId, Account account);

    List<Bookings> getAllTourBookings();

    List<Bookings> getAllTourBookings(String username);

    Bookings deleteTourBooking(Long bookingId);
}
