package org.example.hsf301.dtos.request;

import lombok.Data;
import org.example.hsf301.enums.CCSTATUS;

import java.time.LocalDate;
@Data
public class DeliveryRequest {
    private String customerName;

    private String reason;

    private LocalDate receiveDate;

    private String healthKoiDescription;

    private CCSTATUS status;

    private String address;
}
