package org.example.hsf301.services;

import java.util.List;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.pojos.BookingKoiDetail;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.repos.BookingRepository;
import org.example.hsf301.repos.IBookingRepository;

public class BookingService implements IBookingService {
    private final IBookingRepository repository;
    public BookingService(String name) {
        repository = new BookingRepository(name);
    }
    @Override
    public List<Bookings> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Bookings student) {
        repository.save(student);
    }

    @Override
    public void delete(Long studentID) {
        repository.delete(studentID);
    }

    @Override
    public Bookings findById(Long studentID) {
        return repository.findById(studentID);
    }

    @Override
    public void update(Bookings student) {
        repository.update(student);
    }

    @Override
    public List<Bookings> findByAccountID(String accountID) {
        return repository.findByAccountID(accountID);
    }

    @Override
    public List<BookingKoiDetail> findByBookingID(Long bookingID) {
        return repository.findByBookingID(bookingID);
    }

    @Override
    public void updateBookingStatus(Long bookingID, PaymentStatus status) {
        Bookings bookings = repository.findById(bookingID);
        bookings.setPaymentStatus(status);
        repository.update(bookings);
    }

}
