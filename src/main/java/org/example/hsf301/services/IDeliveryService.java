package org.example.hsf301.services;

import org.example.hsf301.dtos.request.DeliveryRequest;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.Delivery;

import java.util.List;

public interface IDeliveryService {
    Delivery addDelivery(DeliveryRequest deliveryRequest, Long bookingId, Account staff);

    Delivery updateDeliveryHistory(Long deliveryId, DeliveryRequest deliveryRequest) throws Exception;

    void deleteDelivery(Long deliveryId);

    Delivery getDelivery(Long deliveryId);

    Delivery getDeliveryByBookingid(Long bookingId);

    List<Delivery> getAllDeliveries();
}
