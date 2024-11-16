package org.example.hsf301.services;

import org.example.hsf301.enums.CCSTATUS;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.dtos.request.DeliveryRequest;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.pojos.Delivery;
import org.example.hsf301.pojos.Deposit;
import org.example.hsf301.repos.*;

import java.time.LocalDate;
import java.util.List;

public class DeliveryService implements IDeliveryService{
    private final IDeliveryRepository deliveryRepo;
    private final IBookingRepository bookingRepo;
    private final IDepositRepository depositRepo;

    public DeliveryService(String name) {
        deliveryRepo = new DeliveryRepository(name);

        depositRepo = new DepositRepository(name);
        bookingRepo = new BookingRepository(name);
    }


    @Override
    public Delivery addDelivery(DeliveryRequest deliveryRequest, Long bookingId,Account staff) {
        Delivery delivery = new Delivery();
        Bookings bookings = bookingRepo.findById(bookingId);
        if(bookings == null){return null;}
        if(bookings.getDelivery()!=null) return null;
        if(deliveryRequest.getStatus().equals(CCSTATUS.COMPLETED)) {
            bookings.setPaymentStatus(PaymentStatus.COMPLETE);
            bookings.setPaymentDate(LocalDate.now());
            bookingRepo.update(bookings);
        }
        else {
            if(deliveryRequest.getReason().isEmpty()){
                throw new IllegalArgumentException("Cannot add cancelled delivery without reason");
            }
            bookings.setPaymentStatus(PaymentStatus.CANCELLED);
//            bookings.setPaymentDate(LocalDate.now());
            bookingRepo.update(bookings);
        }

        delivery.setDeliveryStaff(staff);
        delivery.setBooking(bookings);
        delivery.setAddress(deliveryRequest.getAddress());
        delivery.setReason(deliveryRequest.getReason());
        delivery.setCustomerName(deliveryRequest.getCustomerName());
        delivery.setHealthKoiDescription(deliveryRequest.getHealthKoiDescription());
        delivery.setStatus(deliveryRequest.getStatus());
        delivery.setReceiveDate(deliveryRequest.getReceiveDate());
        Deposit deposit = depositRepo.findByBookingId(bookingId);
        delivery.setRemainAmount(deposit.getRemainAmount());
        deliveryRepo.save(delivery);
        return delivery;
    }

    @Override
    public Delivery updateDeliveryHistory(Long deliveryId, DeliveryRequest deliveryRequest) throws Exception {
        Delivery delivery = deliveryRepo.findById(deliveryId);
        delivery.setAddress(deliveryRequest.getAddress());
        delivery.setReason(deliveryRequest.getReason());
        delivery.setCustomerName(deliveryRequest.getCustomerName());
        delivery.setHealthKoiDescription(deliveryRequest.getHealthKoiDescription());
//        delivery.setStatus(deliveryRequest.getStatus());
        delivery.setReceiveDate(deliveryRequest.getReceiveDate());
        deliveryRepo.update(delivery);
        return delivery;
    }

    @Override
    public void deleteDelivery(Long deliveryId) {
        deliveryRepo.delete(deliveryId);

    }

    @Override
    public Delivery getDelivery(Long deliveryId) {
        return  deliveryRepo.findById(deliveryId);
    }

    @Override
    public Delivery getDeliveryByBookingid(Long bookingId) {
        List<Delivery> deliveries = deliveryRepo.getAll();
        for(Delivery delivery : deliveries){
            if(delivery.getBooking().getId() == bookingId){
                return delivery;
            }
        }
        return null;
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepo.getAll();
        return deliveries;
    }
}
