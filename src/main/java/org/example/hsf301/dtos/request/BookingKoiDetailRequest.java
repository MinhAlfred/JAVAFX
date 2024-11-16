package org.example.hsf301.dtos.request;

import lombok.Data;

@Data
public class BookingKoiDetailRequest {
    Long farmId;
    Long koiId;
    int quantity;
    float unitPrice;
}
