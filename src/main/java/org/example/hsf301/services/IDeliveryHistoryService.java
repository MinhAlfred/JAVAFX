package org.example.hsf301.services;

import org.example.hsf301.dtos.request.DeliveryHistoryRequest;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.DeliveryHistory;

import java.util.List;

public interface IDeliveryHistoryService {
    DeliveryHistory addDeliveryHistory(DeliveryHistoryRequest deliveryHistoryRequest, Long bookingId, Account staff) throws Exception;

    DeliveryHistory updateDeliveryHistory(Long deliveryHistoryId,DeliveryHistoryRequest deliveryHistoryRequest) throws Exception;

    void deleteDeliveryHistory(Long deliveryHistoryId);

    List<DeliveryHistory> getDeliveryHistory(Long bookingId);

    DeliveryHistory getDeliveryHistoryById(Long deliveryHistoryId);
}
