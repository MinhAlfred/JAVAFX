package org.example.hsf301.dtos.request;

import lombok.Data;

/**
 * @author QuotationRequest
 */
@Data
public class QuotationRequest {
    Long bookingId;
    float amount;
    String description;
}
