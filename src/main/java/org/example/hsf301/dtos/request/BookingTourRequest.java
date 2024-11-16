package org.example.hsf301.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.hsf301.enums.PaymentMethod;

import java.time.LocalDate;
import java.util.List;
@Data

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingTourRequest {
    PaymentMethod paymentMethod;
    List<BookingTourDetailRequest> details;
    Float vat;
    LocalDate bookingDate;
    float discountAmount;
}
