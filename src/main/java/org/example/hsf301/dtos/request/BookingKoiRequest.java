package org.example.hsf301.dtos.request;

import lombok.Data;
import org.example.hsf301.enums.PaymentMethod;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingKoiRequest {

    PaymentMethod paymentMethod;
    List<BookingKoiDetailRequest> details;
    float vat;
    LocalDate bookingDate;
    float discountAmount;
}
