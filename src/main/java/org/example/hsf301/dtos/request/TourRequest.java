package org.example.hsf301.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TourRequest {
     private String tourName;
    private float unitPrice;
    private int maxParticipants;
    private String description;
    private LocalDate startTime;
    private LocalDate endTime;
    private String tourImg;
}
