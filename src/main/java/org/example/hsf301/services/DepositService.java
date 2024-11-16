package org.example.hsf301.services;

import org.example.hsf301.enums.CCSTATUS;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.dtos.request.DepositRequest;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.pojos.Deposit;
import org.example.hsf301.repos.BookingRepository;
import org.example.hsf301.repos.DepositRepository;
import org.example.hsf301.repos.IBookingRepository;
import org.example.hsf301.repos.IDepositRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public class DepositService implements IDepositService{
    private final IBookingRepository bookingRepo;
    private final IDepositRepository depositRepo;
    public DepositService(String name){
        bookingRepo = new BookingRepository(name);
        depositRepo = new DepositRepository(name);
    }
    @Override
    public Deposit getDepositByBookingId(Long bookingId) {
        return depositRepo.findByBookingId(bookingId);
    }

    @Override
    @Transactional
    public void createDeposit(DepositRequest depositRequest, Long bookingId) {
        Deposit deposit = new Deposit();
        Bookings bookings = bookingRepo.findById(bookingId);
        if (bookings == null) {
            throw new IllegalArgumentException("Booking not found with ID: " + bookingId);
        }
        // Check if a deposit already exists for the booking
        if (bookings.getDeposit() != null) {
            throw new IllegalStateException("Deposit already exists for booking ID: " + bookingId);
        }
        deposit.setBooking(bookings);
        deposit.setDepositStatus(CCSTATUS.COMPLETED);
        deposit.setDepositPercentage(depositRequest.getDepositPercentage());
        deposit.setShippingFee(depositRequest.getShippingFee());
        deposit.setShippingAddress(depositRequest.getShippingAddress());
        deposit.setDeliveryExpectedDate(depositRequest.getDeliveryExpectedDate());
        deposit.setDepositDate(LocalDateTime.now());
        deposit.setDepositAmount(bookings.getTotalAmountWithVAT()*depositRequest.getDepositPercentage());
        deposit.setRemainAmount(bookings.getTotalAmountWithVAT()-deposit.getDepositAmount()+deposit.getShippingFee());
        depositRepo.save(deposit);
        bookings.setPaymentStatus(PaymentStatus.SHIPPING);
        bookingRepo.update(bookings);
    }

    @Override
    public void deleteById(Long id) {
        Deposit deposit = depositRepo.findById(id);
        deposit.setDepositStatus(CCSTATUS.CANCELLED);
        if(deposit.getDepositStatus()==CCSTATUS.CANCELLED){
            Bookings bookings = bookingRepo.findById(deposit.getBooking().getId());
            if (bookings == null) {
                throw new IllegalArgumentException("Booking not found");
            }
            bookings.setPaymentStatus(PaymentStatus.CANCELLED);
            bookingRepo.update(bookings);
        }
        depositRepo.update(deposit);
    }

    @Override
    public void updateDeposit(Long id, DepositRequest depositRequest) {
        Deposit deposit = depositRepo.findById(id);
        if(deposit == null){return;}
        deposit.setDeliveryExpectedDate(depositRequest.getDeliveryExpectedDate());

        deposit.setShippingAddress(depositRequest.getShippingAddress());

        deposit.setShippingFee(depositRequest.getShippingFee());

        deposit.setDepositPercentage(depositRequest.getDepositPercentage());

        deposit.setDepositAmount(depositRequest.getDepositPercentage()*deposit.getBooking().getTotalAmountWithVAT());

        deposit.setRemainAmount(deposit.getBooking().getTotalAmountWithVAT()-deposit.getDepositAmount()+deposit.getShippingFee());
        depositRepo.update(deposit);
    }

    @Override
    public List<Deposit> getAllDeposits() {
        return depositRepo.getAll();
    }

    @Override
    public Deposit getDepositById(Long id) {
        return depositRepo.findById(id);
    }
}
