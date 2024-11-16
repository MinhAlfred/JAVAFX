package org.example.hsf301.repos;

import org.example.hsf301.pojos.DeliveryHistory;

import java.util.List;

public interface IDeliveryHistoryRepository {
    List<DeliveryHistory> getAll();

    void save(DeliveryHistory student);

    void delete(Long studentID);

    DeliveryHistory findById(Long studentID);

    void update(DeliveryHistory student);
}
