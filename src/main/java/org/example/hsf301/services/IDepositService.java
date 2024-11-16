package org.example.hsf301.services;

import org.example.hsf301.dtos.request.DepositRequest;
import org.example.hsf301.pojos.Deposit;

import java.util.List;

public interface IDepositService {
    Deposit getDepositByBookingId(Long bookingId);

    void createDeposit(DepositRequest depositRequest, Long bookingId);

    void deleteById(Long id);

    void updateDeposit(Long id, DepositRequest depositRequest);

    List<Deposit> getAllDeposits();

    Deposit getDepositById(Long id);


}
